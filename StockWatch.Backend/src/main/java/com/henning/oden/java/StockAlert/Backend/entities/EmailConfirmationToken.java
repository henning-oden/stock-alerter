package com.henning.oden.java.StockAlert.Backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_confirmation_tokens")
@NoArgsConstructor
public class EmailConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Getter @Setter
    private long userId;

    @Getter @Setter
    private String token;

    private boolean isUsed;

    public EmailConfirmationToken (long userId, String token) {
        this.userId = userId;
        this.token = token;
        isUsed = false;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
