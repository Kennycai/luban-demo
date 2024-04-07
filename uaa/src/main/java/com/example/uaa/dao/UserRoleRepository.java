package com.example.uaa.dao;

import com.example.uaa.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserId(Long userId);
}
