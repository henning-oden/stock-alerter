package com.henning.oden.java.StockAlert.Backend.security;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.jwt.JwtConfigurer;
import com.henning.oden.java.StockAlert.Backend.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    SystemUserRepository users;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        GrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_USER");

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(testAuth);

        SystemUser testSystemUser = new SystemUser("Test", passwordEncoder.encode("password"), authorities);

//        users.save(testSystemUser);



        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(testSystemUser);

        return userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/signup").permitAll()
                .anyRequest().authenticated()
                .and().apply(new JwtConfigurer(jwtTokenProvider));
    }
}
