package com.example.uaa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserRole {
    public static final String ROLE_EDITOR = "EDITOR";
    public static final String ROLE_PRODUCT_ADMIN = "PRODUCT_ADMIN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String role;
    private String description;
}
