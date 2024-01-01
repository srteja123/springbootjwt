package com.example.demo.dto;

public class DashboardResponse {
    private String email;
    private String userType;
    private Long loginCounter;
    private Long id;
    
    public DashboardResponse(String email, String userType, Long loginCounter, Long id) {
        this.email = email;
        this.userType = userType;
        this.loginCounter = loginCounter;
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public Long getLoginCounter() {
        return loginCounter;
    }
    public void setLoginCounter(Long loginCounter) {
        this.loginCounter = loginCounter;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
