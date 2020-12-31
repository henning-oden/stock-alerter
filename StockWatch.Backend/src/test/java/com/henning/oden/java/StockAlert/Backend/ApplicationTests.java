package com.henning.oden.java.StockAlert.Backend;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import com.henning.oden.java.StockAlert.Backend.services.StockService;
import javassist.NotFoundException;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.AfterTestClass;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApplicationTests {

	private Object SystemUserRepository;

	@Autowired
	BeanFactory factory;

	@Test
	void userDetailsFindsDefaultUser() {
		SystemUserRepository repo = (SystemUserRepository) factory.getBean("systemUserRepository");

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		CustomUserDetailsService detailsService = new CustomUserDetailsService(repo, passwordEncoder);

		assertThat(detailsService.loadUserByUsername("Test")).isNotNull();
	}

	@Test
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

	@Test
	void stocksCanBeDeleted() {
		StockService stockService = (StockService) factory.getBean("stockService");



		try {
			Stock stock = stockService.findStockByCode("TEST");
			stockService.deleteStock(stock);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		Exception exception = assertThrows(NotFoundException.class, () -> stockService.findStockByCode("TEST"));
		assertEquals("Stock with code TEST not found", exception.getMessage());

	}

}
