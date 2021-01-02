package com.henning.oden.java.StockAlert.Backend;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import com.henning.oden.java.StockAlert.Backend.repos.StockWatchRepository;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import com.henning.oden.java.StockAlert.Backend.services.StockService;
import javassist.NotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {
	private CustomUserDetailsService detailsService;
	private long stockId;
	private long stockWatchId;

	@Autowired
	BeanFactory factory;

	@Test
	@Order(1)
	void userDetailsFindsDefaultUser() {
		SystemUserRepository repo = (SystemUserRepository) factory.getBean("systemUserRepository");

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		detailsService = new CustomUserDetailsService(repo, passwordEncoder);

		assertThat(detailsService.loadUserByUsername("Test")).isNotNull();
	}

	@Test
	@Order(2)
	void stocksCanBeCreated() {
		StockService stockService = (StockService) factory.getBean("stockService");

		Stock stock = new Stock("TEST", "test stock");

		stockService.saveStock(stock);

		try {
			assertThat(stockService.findStockByCode("TEST")).isNotNull();
			assertThat(stockService.findStockByCommonName("test stock")).isNotNull();
		} catch (NotFoundException e) {
			throw new AssertionError("Unexpected exception thrown by test.");
		}

	}

	// Integration tests for StockWatch class.
	@Test
	@Order(3)
	void stockWatchCanBeCreated() throws NotFoundException {
		StockRepository stockRepository = (StockRepository) factory.getBean("stockRepository");

		SystemUserRepository userRepository = (SystemUserRepository) factory.getBean("systemUserRepository");

		StockWatchRepository stockWatchRepository = (StockWatchRepository) factory.getBean("stockWatchRepository");

		detailsService = new CustomUserDetailsService(userRepository, new BCryptPasswordEncoder());

		SystemUser user = (SystemUser) detailsService.loadUserByUsername("Test");

		Stock stock = stockRepository.findByCode("TEST").get();

		StockWatch stockWatch = new StockWatch(user.getId(), stock.getId(), 100, 110, 5, 5);
		StockWatch savedStockWatch = stockWatchRepository.save(stockWatch);
		stockWatchId = savedStockWatch.getId();

		boolean stockWatchExists = stockWatchRepository.findById(stockWatchId).isPresent();

		assertThat(stockWatchExists).isTrue();
	}

	@Test
	@Order(3)
	void stockWatchCanBeDeleted() {
		StockRepository stockRepository = (StockRepository) factory.getBean("stockRepository");
		StockWatchRepository watchRepository = (StockWatchRepository) factory.getBean("stockWatchRepository");

		Stock stock = stockRepository.findByCode("TEST").get();

		StockWatch watch = (StockWatch) watchRepository.findByStockId(stock.getId()).stream().findFirst().get()
													   .toArray()[0];

		stockWatchId = watch.getId();

		assertThat(watchRepository.findById(stockWatchId).isPresent()).isTrue();

		watchRepository.delete(watch);

		assertThat(watchRepository.findById(stockWatchId).isPresent()).isFalse();
	}

	@Test
	@Order(5)
	void stocksCanBeDeleted() throws NotFoundException {
		StockService stockService = (StockService) factory.getBean("stockService");

		Stock stock = stockService.findStockByCode("TEST");
		// Trying to delete the stock should succeed with no exception
		stockService.deleteStock(stock);

		// Trying to find the stock again after deleting it should throw exception
		Exception exception = assertThrows(NotFoundException.class, () -> stockService.findStockByCode("TEST"));
		assertEquals("Stock with code TEST not found", exception.getMessage());

	}

}
