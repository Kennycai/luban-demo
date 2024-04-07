package com.example.commonutils.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleInfo {
    private String username;
    private List<String> roles;

}
