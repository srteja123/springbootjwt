package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DashboardResponse;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class Dashboard {
    @Autowired
    AuthTokenFilter authFilter;
     @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/view")
    public ResponseEntity<Object> show_payments(HttpServletRequest request){
        try{
        String jwt = authFilter.parseJwt(request);
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
          String emailId = jwtUtils.getUserNameFromJwtToken(jwt);
          UserInfo userObj =userRepository.findByUserEmail(emailId);
		return ResponseEntity.ok(new DashboardResponse(
                    userObj.getUserEmail(),
                    userObj.getUserType(),
                    userObj.getLoginCounter(),
                    userObj.getUserId()
                    )); 
        }else{
           return ResponseEntity.ok("Token is not valid"); 
        }
        }catch(Exception e){
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }
       
  
    }


}
