package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long>{
   Boolean existsByUserEmail(String userEmail);
     UserInfo findByUserEmail(String userEmail);
}