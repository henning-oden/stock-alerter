package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private SystemUserRepository users;

    public CustomUserDetailsService(SystemUserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.findByUsername(username)
                .orElseThrow(() -> new
                        UsernameNotFoundException("Username: " + username + " not found"));
    }
}
