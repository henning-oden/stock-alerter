package com.henning.oden.java.StockAlert.Backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
    private static final long serializableVersionUID = -6545648414564L;
    @Getter @Setter
    private String username, password;
}


