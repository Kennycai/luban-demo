package com.example.uaa.dao;

import com.example.uaa.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    List<Authorities> findByRole(String role);
}
