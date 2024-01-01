package com.example.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DashboardResponseTest {

    @Test
    public void testDashboardResponse() {
        // Mock user data
        String email = "test@example.com";
        String userType = "user";
        Long loginCounter = 5L;
        Long id = 1L;

        // Create an instance of DashboardResponse
        DashboardResponse dashboardResponse = new DashboardResponse(email, userType, loginCounter, id);

        // Test getter methods
        assertEquals(email, dashboardResponse.getEmail());
        assertEquals(userType, dashboardResponse.getUserType());
        assertEquals(loginCounter, dashboardResponse.getLoginCounter());
        assertEquals(id, dashboardResponse.getId());

        // Test setter methods
        String newEmail = "new@example.com";
        dashboardResponse.setEmail(newEmail);
        assertEquals(newEmail, dashboardResponse.getEmail());

        String newUserType = "admin";
        dashboardResponse.setUserType(newUserType);
        assertEquals(newUserType, dashboardResponse.getUserType());

        Long newLoginCounter = 10L;
        dashboardResponse.setLoginCounter(newLoginCounter);
        assertEquals(newLoginCounter, dashboardResponse.getLoginCounter());

        Long newId = 2L;
        dashboardResponse.setId(newId);
        assertEquals(newId, dashboardResponse.getId());
    }
}

