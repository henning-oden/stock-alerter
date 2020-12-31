package com.henning.oden.java.StockAlert.Backend;

import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
