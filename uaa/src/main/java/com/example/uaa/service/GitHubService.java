package com.example.uaa.service;

import com.example.uaa.dto.GitHubUserDto;
import com.example.uaa.entity.Users;

public interface GitHubService {
    String getAccessToken(String code);

    GitHubUserDto getGitHubUser(String accessToken);

    String getGitHubLoginUrl();

    Users loadByGitHubUser(GitHubUserDto gitHubUserDto);
}
