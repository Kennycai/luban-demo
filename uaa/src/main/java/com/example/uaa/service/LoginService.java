package com.example.uaa.service;

import com.example.uaa.dto.LoginForm;

public interface LoginService {

    String login(LoginForm loginForm);

    String githubCallback(String code);
}
