package com.example.uaa.iservice;

import com.example.uaa.config.FeignConfig;
import com.example.uaa.dto.AccessToken;
import com.example.uaa.dto.GitHubUserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "github", url = "https://github.com", configuration = FeignConfig.class)
public interface GitHubClient {

    @PostMapping(value = "/login/oauth/access_token", consumes = "application/json", produces = "application/json")
    @Headers("Accept: application/json")
    AccessToken getAccessToken(@RequestParam("client_id") String clientId,
                               @RequestParam("client_secret") String clientSecret,
                               @RequestParam("code") String code);

    @GetMapping(value = "/user")
    @Headers({
            "Accept: application/vnd.github+json",
            "X-GitHub-Api-Version: 2022-11-28"
    })
    GitHubUserDto getGitHubUser(URI uri, @RequestHeader("Authorization") String token);
}
