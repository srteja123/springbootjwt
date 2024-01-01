package com.example.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.entity.UserInfo;

public class AdminDashBoardResponseTest {

    @Test
    public void testAdminDashBoardResponse() {
        // Mock user data
        String email = "test@example.com";
        String userType = "admin";
        Long loginCounter = 5L;
        Long id = 1L;
        List<UserInfo> userList = new ArrayList<>(); // Mocking a list of user info

        // Create an instance of AdminDashBoardResponse
        AdminDashBoardResponse adminResponse = new AdminDashBoardResponse(email, userType, loginCounter, id, userList);

        // Test getter methods
        assertEquals(email, adminResponse.getEmail());
        assertEquals(userType, adminResponse.getUserType());
        assertEquals(loginCounter, adminResponse.getLoginCounter());
        assertEquals(id, adminResponse.getId());
        assertEquals(userList, adminResponse.getUserList());

        // Test setter methods
        String newEmail = "new@example.com";
        adminResponse.setEmail(newEmail);
        assertEquals(newEmail, adminResponse.getEmail());

        String newUserType = "user";
        adminResponse.setUserType(newUserType);
        assertEquals(newUserType, adminResponse.getUserType());

        Long newLoginCounter = 10L;
        adminResponse.setLoginCounter(newLoginCounter);
        assertEquals(newLoginCounter, adminResponse.getLoginCounter());

        Long newId = 2L;
        adminResponse.setId(newId);
        assertEquals(newId, adminResponse.getId());

        List<UserInfo> newUserList = new ArrayList<>(); // Mocking a new list of user info
        adminResponse.setUserList(newUserList);
        assertEquals(newUserList, adminResponse.getUserList());
    }
}
