package com.example.demo.dto;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private List<String> roles;
  private Long loginCounter;

  public JwtResponse(String accessToken, Long id, String email,Long loginCounter,  List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.email = email;
    this.loginCounter = loginCounter;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public List<String> getRoles() {
    return roles;
  }

  public Long getLoginCounter() {
    return loginCounter;
  }

  public void setLoginCounter(Long loginCounter) {
    this.loginCounter = loginCounter;
  }
  
}