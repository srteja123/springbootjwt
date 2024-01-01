package com.example.demo.dto;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private List<String> roles;
  private Long loginCounter;
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
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
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
  public Long getLoginCounter() {
    return loginCounter;
  }
  public void setLoginCounter(Long loginCounter) {
    this.loginCounter = loginCounter;
  }
  
  
  public JwtResponse(String token, Long id, String email,  Long loginCounter,List<String> roles) {
    this.token = token;
    this.id = id;
    this.email = email;
    this.roles = roles;
    this.loginCounter = loginCounter;
  }

  
  
}
