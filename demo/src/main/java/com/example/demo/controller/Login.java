package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserDetailsImpl;

//Email and password are authenticated here and data is being prepared and sent to dashboard
@RestController
@RequestMapping("/user")
@CrossOrigin
public class Login {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        HashMap<String, Object> api_response = new HashMap<String, Object>();
        try {
            Boolean emailExists = userRepository.existsByEmailId(request.getEmailId());

            if(!emailExists){
                api_response.put("status", "failed");
                api_response.put("message", "User does not exist.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(api_response);

            }else{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
             List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            if(userDetails.getAccountStatus().equals("ACTIVE")){
                UserInfo create_record = userRepository.findByEmailId(userDetails.getEmail());
                Long counter = userDetails.getLoginCounter() + 1L;
                create_record.setLoginCounter(counter);
                userDetails.setLoginCounter(counter);
                userRepository.save(create_record);
                return ResponseEntity.ok(new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getEmail(),
                        userDetails.getLoginCounter(),
                        roles));
            }else{
                api_response.put("status", "failed");
                api_response.put("message", "User account is de-activated");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(api_response);
            }
        }
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Bad credentials")) {
                api_response.put("status", "failed");
                api_response.put("message", e.getMessage());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(api_response);
            } else {
                api_response.put("status", "failed");
                api_response.put("message", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api_response);
            }

        }
    }
}
