// TODO: Overhaul or remove this implementation of concepts presented by SeunMatt in https://github.com/SeunMatt/spring-blog/blob/master/src/main/java/com/smatt/seeders/DatabaseSeeder.java
package com.henning.oden.java.StockAlert.Backend.util.dev;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class DataSeeder {
    private SystemUserRepository users;
    private PasswordEncoder passwordEncoder;

    public DataSeeder(SystemUserRepository users, PasswordEncoder encoder) {
        this.users = users;
        passwordEncoder = encoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent e) {
        seedTestUser();
    }

    private void seedTestUser() {
        if (users.findAll().isEmpty())
            this.users.save(new SystemUser("Test", passwordEncoder.encode("password"), new ArrayList<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("USER")))));
    }
}
