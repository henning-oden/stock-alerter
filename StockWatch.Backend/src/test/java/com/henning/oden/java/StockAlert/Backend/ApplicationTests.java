package com.henning.oden.java.StockAlert.Backend;

import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApplicationTests {

	private Object SystemUserRepository;

	@Test
	void userDetailsFindsDefaultUser() {
		SystemUserRepository repo = Mockito.mock(SystemUserRepository.class);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		CustomUserDetailsService detailsService = new CustomUserDetailsService(repo, passwordEncoder);

		assertThat(detailsService.loadUserByUsername("Test")).isNotNull();
	}

}
