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
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class Register {

    @Autowired
    private UserRepository pre_signup_query;
    
    @PostMapping("/register")
    public ResponseEntity<Object> create_account(@RequestBody RegisterRequest request){ 
       //Code to be written to check if duplicate email Id is present
            if(pre_signup_query.existsByUserEmail(request.getEmailId())){
                return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
            }else{
                Date presentTime = new java.util.Date();
                String hashed_password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
                    UserInfo create_record = new UserInfo();
                    create_record.setFirstName(request.getFirstName());
                    create_record.setLastName(request.getLastName());
                    create_record.setUserEmail(request.getEmailId());
                    create_record.setUserType("USER");
                    create_record.setPassword(hashed_password);
                    create_record.setAccountStatus("ACTIVE");
                    create_record.setCreatedTime(presentTime);
                    pre_signup_query.save(create_record);

                    //After successful registration redirect to login page
            }
            return null;

                  //  validateRequest.put("status", "success");
                  //  validateRequest.put("message", "User Registration was successful");
                    //return ResponseEntity.status(HttpStatus.OK).body(validateRequest);
                
            
        
    }
    
}
