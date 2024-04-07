package com.example.uaa.dao;

import com.example.uaa.entity.GitHubUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubUserRepository extends JpaRepository<GitHubUser, Long> {
}
