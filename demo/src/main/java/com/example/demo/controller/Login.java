package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.repository.AuthRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserDetailsImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class Login {
    @Autowired
    private UserRepository users_query;
    @Autowired
    private AuthRepo auth_token_query;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /* @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request){
        HashMap<String, Object> api_response = new HashMap<String,Object>();
        try {
            
                String email = request.getEmailId();
                Optional<UserInfo> userInfo = users_query.findById(request.getUserId());
                String plain_password = request.getPassword();
                String hashed_password = userInfo.get().getPassword();
                if (BCrypt.checkpw(plain_password, hashed_password)){
                    HashMap<String, String> auth_response = new HashMap<String,String>();
                    auth_response = Auth.create_auth_token(userInfo.get().getUserId(),5);

                    AuthToken create_record = new AuthToken();
                    create_record.setUser_id(userInfo.get().getUserId());
                    create_record.setMerchant("B2B");
                    create_record.setToken(auth_response.get("token"));
                    create_record.setTokenCreatedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(auth_response.get("token_created_time")));
                    create_record.setTokenExpiryTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(auth_response.get("token_expiry_time")));
                    create_record.setStatus("VALID");
                    create_record.setCreatedTime(TimeStamp.fetch_present_time());
                    auth_token_query.save(create_record);

                    String auth_token_created = Base64.getEncoder().encodeToString(auth_response.get("token").getBytes())+"."+Auth.generate_signature(auth_response.get("token"));
                    api_response.put("status", "success");
                    api_response.put("message", "Authentication Successful");
                    api_response.put("token", auth_token_created);
                    return ResponseEntity.status(HttpStatus.OK).body(api_response);
                }else{
                    api_response.put("status", "failed");
                    api_response.put("message", "password is wrong!");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(api_response);
                }
        } catch (Exception e) {
            api_response.put("status", "failed");
            api_response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api_response);
        }
        
    } */

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

              return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    roles)); 
                    
        } catch (Exception e) {
            // TODO: handle exception
            
            return ResponseEntity.ok("CATCH OUT " + e.getMessage());
        }
    }
}