package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auth_tokens")
public class AuthToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column
    private String token;
    @Column(name = "token_created_time")
	private Date tokenCreatedTime;
    @Column(name = "token_expiry_time")
	private Date tokenExpiryTime;
    @Column(name = "merchant")
    private String merchant;
    @Column
    private String status;
    @Column(name = "created_time")
	private Date createdTime;
    @Column(name = "updated_time")
	private Date updatedTime;

    public AuthToken(){
        
    }

    public AuthToken(Long id, Long userId, String token, Date tokenCreatedTime, Date tokenExpiryTime, String merchant,
            String status, Date createdTime, Date updatedTime) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.tokenCreatedTime = tokenCreatedTime;
        this.tokenExpiryTime = tokenExpiryTime;
        this.merchant = merchant;
        this.status = status;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUser_id() {
        return userId;
    }
    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Date getTokenCreatedTime() {
        return tokenCreatedTime;
    }
    public void setTokenCreatedTime(Date tokenCreatedTime) {
        this.tokenCreatedTime = tokenCreatedTime;
    }
    public Date getTokenExpiryTime() {
        return tokenExpiryTime;
    }
    public void setTokenExpiryTime(Date tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }
    public String getMerchant() {
        return merchant;
    }
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
