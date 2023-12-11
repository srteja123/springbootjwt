package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AuthToken;

@Repository
public interface AuthRepo extends JpaRepository<AuthToken, Long>{
    AuthToken findByUserId(int user_id);
    AuthToken findByToken(String token);
}

