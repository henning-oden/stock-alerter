package com.henning.oden.java.StockAlert.Backend;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import com.henning.oden.java.StockAlert.Backend.repos.StockWatchRepository;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import com.henning.oden.java.StockAlert.Backend.services.StockService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import javassist.NotFoundException;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.enums.BarsTimeFrame;
import net.jacobpeterson.alpaca.rest.exception.AlpacaAPIRequestException;
import net.jacobpeterson.domain.alpaca.marketdata.Bar;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {
	private CustomUserDetailsService detailsService;
	private long stockId;
	private long stockWatchId;

	@Value( "${alpaca.key_id}" )
	private String keyId;

	@Value( "${alpaca.secret}" )
	private String secret;

	@Value( "${alpaca.base_api_url}" )
	private String alpacaBaseUrl;

	@Value( "${alpaca.base_data_url]" )
	private String alpacaDataUrl;

	@Value( "${sendgrid.api_key}" )
	private String sendGridKey;

	@Autowired
	BeanFactory factory;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	@Test
	@Order(1)
	void userDetailsFindsDefaultUser() {
		SystemUserRepository repo = (SystemUserRepository) factory.getBean("systemUserRepository");

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		detailsService = new CustomUserDetailsService(repo);

		assertThat(detailsService.loadUserByUsername("Test")).isNotNull();
	}

	@Test
	@Order(2)
	void stocksCanBeCreated() {
		StockService stockService = (StockService) factory.getBean("stockService");

		Stock stock = new Stock("TEST", "test stock");

		stockService.saveStock(stock);

		assertThat(stockService.findStockByCode("TEST").isPresent()).isTrue();
		assertThat(stockService.findStockByCommonName("test stock").isPresent()).isTrue();

	}

	// Integration tests for StockWatch class.
	@Test
	@Order(3)
	void stockWatchCanBeCreated() throws NotFoundException {
		StockRepository stockRepository = (StockRepository) factory.getBean("stockRepository");

		SystemUserRepository userRepository = (SystemUserRepository) factory.getBean("systemUserRepository");

		StockWatchRepository stockWatchRepository = (StockWatchRepository) factory.getBean("stockWatchRepository");

		authenticationManager = (AuthenticationManager) factory.getBean("authenticationManagerBean");

		jwtTokenProvider = (JwtTokenProvider) factory.getBean("jwtTokenProvider");

		detailsService = new CustomUserDetailsService(userRepository);

		SystemUser user = (SystemUser) detailsService.loadUserByUsername("Test");

		Stock stock = stockRepository.findByCode("TEST").get();

		StockWatch stockWatch = new StockWatch(user.getId(), stock.getId(), BigDecimal.valueOf(100), BigDecimal.valueOf(110), 5);
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

		StockWatch watch = (StockWatch) watchRepository.findByStockId(stock.getId()).stream().findFirst().get();

		stockWatchId = watch.getId();

		assertThat(watchRepository.findById(stockWatchId).isPresent()).isTrue();

		watchRepository.delete(watch);

		assertThat(watchRepository.findById(stockWatchId).isPresent()).isFalse();
	}

	@Test
	@Order(5)
	void stocksCanBeDeleted() throws NotFoundException {
		StockService stockService = (StockService) factory.getBean("stockService");

		Optional<Stock> stock = stockService.findStockByCode("TEST");
		// Trying to delete the stock should succeed
		stockService.deleteStock(stock.get());

		// Trying to find the stock again after deleting it should return an empty optional
		Optional<Stock> deletedStock = stockService.findStockByCode("TEST");
		assertThat(deletedStock.isEmpty()).isTrue();
	}

	@Test
	@Order(6)
	void alpacaGetsBar() throws NotFoundException {
		StockService stockService = (StockService) factory.getBean("stockService");
		AlpacaAPI alpacaAPI = new AlpacaAPI(keyId, secret, alpacaBaseUrl);
		ZonedDateTime startTime = ZonedDateTime.of(2020,12,31,10,0,0,0, ZoneId.of("America/New_York"));
		ZonedDateTime endTime = ZonedDateTime.of(2020,12,31,10,10,0,0, ZoneId.of("America/New_York"));
		Bar bar = null;
		try {
			Map<String, ArrayList<Bar>> bars = alpacaAPI.getBars(BarsTimeFrame.ONE_MIN, "AAPL", null, startTime, endTime, null, null);
			bar =  bars.get("AAPL").get(0);
			bars.values().forEach((b) -> System.out.println(b.toString()));
		} catch (AlpacaAPIRequestException e) {
			e.printStackTrace();
		}
		assertThat(bar).isNotNull();
	}

	@Test
	void sendgridLogin() {
		SendGrid sendGrid = new SendGrid(sendGridKey);
		try {
			Request request = new Request();
			request.setMethod(Method.GET);
			request.setEndpoint("api_keys");
			Response response = sendGrid.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
