package com.example.uaa.service;

import com.example.uaa.dao.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginBaseService {
    // 生成随机不重复用户名
    @Autowired
    private UsersRepository usersRepository;
    protected String generateUsername(String username) {
        String usernameWithSuffix = username + (int) (Math.random() * 1000000);
        if (usersRepository.findByUsername(usernameWithSuffix) != null) {
            return generateUsername(username);
        }
        return usernameWithSuffix;
    }
}
