package com.example.uaa.service;

import com.example.uaa.config.GitHubConfig;
import com.example.uaa.dao.UserRoleRepository;
import com.example.uaa.dao.UsersRepository;
import com.example.uaa.dto.AccessToken;
import com.example.uaa.dto.GitHubUserDto;
import com.example.uaa.entity.UserRole;
import com.example.uaa.entity.Users;
import com.example.uaa.iservice.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitHubServiceImpl extends LoginBaseService implements GitHubService {
    @Autowired
    GitHubConfig gitHubConfig;
    @Autowired
    private GitHubClient gitHubClient;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public String getAccessToken(String code) {
        // 使用code向GitHub请求访问令牌
        AccessToken accessToken = gitHubClient.getAccessToken(gitHubConfig.getClientId(), gitHubConfig.getClientSecret(), code);
        return accessToken.getAccessToken();
    }

    @Override
    public GitHubUserDto getGitHubUser(String accessToken) {
        URI uri = null;
        try {
            uri = new URI("https://api.github.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用访问令牌获取GitHub用户信息
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + accessToken);
        headerMap.put("Accept", "application/vnd.github+json");
        headerMap.put("X-GitHub-Api-Version", "2022-11-28");
        return gitHubClient.getGitHubUser(uri, "Bearer " + accessToken);
    }

    @Override
    public String getGitHubLoginUrl() {
        return String.format("https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s",
                gitHubConfig.getClientId(), gitHubConfig.getRedirectUri());
    }

    @Override
    @Transactional
    public Users loadByGitHubUser(GitHubUserDto gitHubUserDto) {
        Users users = usersRepository.findByPlatformIdAndUserType(String.valueOf(gitHubUserDto.getId()), Users.USER_TYPE_GITHUB);
        if (users == null) {
            users = new Users();
            users.setPlatformId(String.valueOf(gitHubUserDto.getId()));
            users.setUsername(generateUsername(gitHubUserDto.getLogin()));
            users.setUserType(Users.USER_TYPE_GITHUB);
            usersRepository.save(users);
            UserRole userRole = new UserRole();
            userRole.setUserId(users.getUserId());
            userRole.setRole(UserRole.ROLE_PRODUCT_ADMIN);
            userRoleRepository.save(userRole);
        }
        return users;
    }


}
