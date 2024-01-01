package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name="user_info")
public class UserInfo {

   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="User_ID")
    private Long userId;

    @Column(name="user_type",nullable=false)
    private String userType;

    @Column(name="user_email",nullable = false,unique = true)
    private String emailId;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="first_name",nullable=false)
    private String firstName;

    @Column(name="last_name",nullable=false)
    private String lastName;

    @Column(name="account_status" , nullable = true)
    private String accountStatus;

    @Column(name="created_time" , nullable = true)
    private Date createdTime;

     @Column(name="updated_time" , nullable = true)
    private Date updatedTime;

    @Column(name="login_counter" , nullable = true)
    private Long loginCounter;


    public UserInfo(Long userId, String userType, String emailId, String password, String firstName, String lastName,
            String accountStatus, Date createdTime) {
        this.userId = userId;
        this.userType = userType;
        this.emailId = emailId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountStatus = accountStatus;
        this.createdTime = createdTime;
    }

    public UserInfo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getLoginCounter() {
        return loginCounter;
    }

    public void setLoginCounter(Long loginCounter) {
        this.loginCounter = loginCounter;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
