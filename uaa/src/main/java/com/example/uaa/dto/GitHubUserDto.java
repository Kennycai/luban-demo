package com.example.uaa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubUserDto {
    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
