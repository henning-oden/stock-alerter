// TODO: Overhaul or remove this implementation of concepts presented by SeunMatt in https://github.com/SeunMatt/spring-blog/blob/master/src/main/java/com/smatt/seeders/DatabaseSeeder.java
package com.henning.oden.java.StockAlert.Backend.util.dev;

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockRepository;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import liquibase.pro.packaged.S;
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
    private StockRepository stocks;

    public DataSeeder(SystemUserRepository users, StockRepository stocks, PasswordEncoder encoder) {
        this.users = users;
        this.stocks = stocks;
        passwordEncoder = encoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent e) {
        seedTestUser();
        seedStocks();
    }

    private void seedTestUser() {
        if (users.findAll().isEmpty())
            users.save(new SystemUser("Test", passwordEncoder.encode("password"), new ArrayList<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("USER")))));
    }

    private void seedStocks() {
        if (stocks.findAll().isEmpty()) {
            stocks.save(new Stock("AAPL", "Apple"));
            stocks.save(new Stock("GOOGL", "Alphabet Inc Class A"));
            stocks.save(new Stock("MSFT", "Microsoft"));
        }
    }
}
