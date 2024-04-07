package com.example.uaa.controller;

import com.example.commonutils.dto.IResponse;
import com.example.uaa.dto.LoginForm;
import com.example.uaa.service.GitHubService;
import com.example.uaa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private GitHubService gitHubService;

    @GetMapping
    public String login(Model model) {
        model.addAttribute("githubLoginUrl", gitHubService.getGitHubLoginUrl());
        return "login";
    }

    @PostMapping
    @ResponseBody
    public IResponse<String> login(@RequestBody LoginForm loginForm) {
        return IResponse.ok(loginService.login(loginForm));
    }

    @GetMapping("/oauth/redirect")
    public String callback(@RequestParam String code, Model model) {
        String token = loginService.githubCallback(code);
        model.addAttribute("token", token);
        return "login";
    }

    @GetMapping("/githubLogin")
    public String githubLogin() {
        return "redirect:" + gitHubService.getGitHubLoginUrl();
    }
}
