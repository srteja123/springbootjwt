package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDashBoardResponse;
import com.example.demo.dto.DashboardResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

// Below Code to fetch data to dashboard based on the email id decrypted from the token
@RestController
@RequestMapping("/user")
@CrossOrigin
public class Dashboard {
    @Autowired
    AuthTokenFilter authFilter;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/dashboard")
    public ResponseEntity<Object> show_data(HttpServletRequest request) {
        try {
            String jwt = authFilter.parseJwt(request);
            // Verify the jwt token
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Email id from jwt token is used for fetching the user data
                String emailId = jwtUtils.getUserNameFromJwtToken(jwt);
                UserInfo userObj = userRepository.findByEmailId(emailId);
                return ResponseEntity.ok(new DashboardResponse(
                        userObj.getEmailId(),
                        userObj.getUserType(),
                        userObj.getLoginCounter(),
                        userObj.getUserId()));
            } else {
                return ResponseEntity.ok("Token is not valid");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }

    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Object> show_admindata(HttpServletRequest request) {
        try {
            String jwt = authFilter.parseJwt(request);
            // Verify the jwt token
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String adminEmailId = jwtUtils.getUserNameFromJwtToken(jwt);
                UserInfo adminUserObj = userRepository.findByEmailId(adminEmailId);
                List<UserInfo> userObjList = userRepository.findAll();
                return ResponseEntity.ok(new AdminDashBoardResponse(
                        adminUserObj.getEmailId(),
                        adminUserObj.getUserType(),
                        adminUserObj.getLoginCounter(),
                        adminUserObj.getUserId(),userObjList));
            } else {
                return ResponseEntity.ok("Token is not valid");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }

    }

    @PostMapping("/admin/deleteUser")
    public ResponseEntity<Object> deleteUser (@RequestBody UserRequest request) {
        try {
            // Verify the jwt token
            if (request.getUserType().equals("ADMIN")) {
                userRepository.deleteById(request.getUserId());
                List<UserInfo> userObjList = userRepository.findAll();

                return ResponseEntity.ok(userObjList);
            } else {
                return ResponseEntity.ok("User has no access to delete the user");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }

    }

    @PostMapping("/admin/editUser")
    public ResponseEntity<Object> editUser (@RequestBody UserRequest request) {
        try {
                Date presentTime = new java.util.Date();
                UserInfo create_record = new UserInfo();
                create_record.setFirstName(request.getFirstName());
                create_record.setLastName(request.getLastName());
                create_record.setEmailId(request.getEmailId());
                create_record.setUserType(request.getUserType());
                create_record.setAccountStatus(request.getAccountStatus());
                create_record.setPassword(request.getPassword());
                create_record.setCreatedTime(request.getCreatedTime());
                create_record.setUpdatedTime(presentTime);
                create_record.setUserId(request.getUserId());
                create_record.setLoginCounter(request.getLoginCounter());
                UserInfo updatedUser=userRepository.save(create_record);
                return ResponseEntity.ok(updatedUser);
            
        } catch (Exception e) {
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }

    }

}
