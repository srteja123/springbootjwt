package com.example.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class JwtResponseTest {

    @Test
    public void testJwtResponseGettersAndSetters() {
        // Test data
        String token = "exampleToken";
        Long id = 1L;
        String email = "test@example.com";
        Long loginCounter = 5L;
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        // Create a JwtResponse instance
        JwtResponse jwtResponse = new JwtResponse(token, id, email, loginCounter, roles);

        // Test getter methods
        assertEquals(token, jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(email, jwtResponse.getEmail());
        assertEquals(roles, jwtResponse.getRoles());
        assertEquals(loginCounter, jwtResponse.getLoginCounter());

        // Test setter methods
        String newToken = "newToken";
        jwtResponse.setToken(newToken);
        assertEquals(newToken, jwtResponse.getToken());

        String newEmail = "new@example.com";
        jwtResponse.setEmail(newEmail);
        assertEquals(newEmail, jwtResponse.getEmail());

        Long newId = 2L;
        jwtResponse.setId(newId);
        assertEquals(newId, jwtResponse.getId());

        Long newLoginCounter = 10L;
        jwtResponse.setLoginCounter(newLoginCounter);
        assertEquals(newLoginCounter, jwtResponse.getLoginCounter());

        List<String> newRoles = new ArrayList<>();
        newRoles.add("ROLE_ADMIN");
        jwtResponse.setRoles(newRoles);
        assertEquals(newRoles, jwtResponse.getRoles());
    }
}
