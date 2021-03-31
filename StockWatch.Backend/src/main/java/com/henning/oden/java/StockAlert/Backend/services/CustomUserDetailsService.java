package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    public CustomUserDetailsService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    public Optional<SystemUser> findById(long userId) {
        return systemUserRepository.findById(userId);
    }

    public SystemUser save(SystemUser systemUser) {
        SystemUser savedSystemUser = systemUserRepository.saveAndFlush(systemUser);
        return savedSystemUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.systemUserRepository.findByUsername(username)
                .orElseThrow(() -> new
                        UsernameNotFoundException("Username: " + username + " not found"));
    }

    public void verifyUserEmail(SystemUser user) {
        user.setEmailVerified(true);
        save(user);
    }

    public void deleteUser(SystemUser user) {
        systemUserRepository.delete(user);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "CustomUserDetailsService{" +
                "systemUserRepository=" + systemUserRepository +
                '}';
    }
}
