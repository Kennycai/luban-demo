package com.example.uaa.dao;

import com.example.uaa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findByPlatformIdAndUserType(String id, String userTypeUser);

    Users findByUsernameAndUserType(String fullName, String userTypeLdap);
}
