package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private SystemUserRepository users;

    private final List<SystemUser> usersInMemory = new ArrayList<>();

    private PasswordEncoder passwordEncoder;
    public CustomUserDetailsService(SystemUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        usersInMemory.add(new SystemUser("Test", passwordEncoder.encode("password"), new ArrayList<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("USER")))));
    }

    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersInMemory.stream().filter(u -> u.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new
                        UsernameNotFoundException("Username: " + username + " not found"));
    }
}
