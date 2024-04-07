package com.example.uaa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {
    public static final String USER_TYPE_GITHUB = "GITHUB";
    public static final String USER_TYPE_LDAP = "LDAP";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private boolean enabled;

    private String userType;
    private String platformId;
}
