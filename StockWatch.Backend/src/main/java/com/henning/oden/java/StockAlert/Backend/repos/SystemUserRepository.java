package com.henning.oden.java.StockAlert.Backend.repos;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);
}
