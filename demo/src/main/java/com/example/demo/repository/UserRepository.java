package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
  Boolean existsByEmailId(String userEmail);

  UserInfo findByEmailId(String userEmail);
}
