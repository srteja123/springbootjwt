package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
//Below code used for registering the user and also to validate if the registered email already exists in the DB
@RestController
@RequestMapping("/user")
@CrossOrigin
public class Register {

    @Autowired
    private UserRepository pre_signup_query;
    
    @PostMapping("/register")
    public ResponseEntity<Object> create_account(@RequestBody RegisterRequest request){
        try{
       //Code to be written to check if duplicate email Id is present
            if(pre_signup_query.existsByEmailId(request.getEmailId())){
                return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
            }else{
                Date presentTime = new java.util.Date();
                String hashed_password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
                    UserInfo create_record = new UserInfo();
                    create_record.setFirstName(request.getFirstName());
                    create_record.setLastName(request.getLastName());
                    create_record.setEmailId(request.getEmailId());
                    create_record.setUserType("USER");
                    create_record.setPassword(hashed_password);
                    create_record.setAccountStatus(request.getAccountStatus());
                    create_record.setLoginCounter(0L);
                    create_record.setCreatedTime(presentTime);
                    pre_signup_query.save(create_record);
                    return ResponseEntity.ok(new MessageResponse("Registration is successful"));
            }
        }catch(Exception e){
            return ResponseEntity.ok("CATCH OUT Register" + e.getMessage());
        }
    }
    
}
