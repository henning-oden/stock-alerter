package com.henning.oden.java.StockAlert.Backend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtConfigurer(JwtTokenProvider jwtAuthenticationService, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtAuthenticationService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, authenticationManager);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "JwtConfigurer{" +
                "jwtTokenProvider=" + jwtTokenProvider +
                ", authenticationManager=" + authenticationManager +
                '}';
    }
}
