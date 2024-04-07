package com.example.uaa.dto;

import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private Type type;

    public enum Type {
        FORM, LDAP
    }
}
