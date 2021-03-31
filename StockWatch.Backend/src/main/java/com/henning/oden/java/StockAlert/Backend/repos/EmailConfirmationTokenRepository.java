package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {
    EmailConfirmationToken findByToken(String token);
}
