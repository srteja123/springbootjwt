package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.repository.UserRepository;

public class RegisterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Register registerController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    public void testCreateAccount_NewUserRegistration() {
        // Prepare test data for a new user registration
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmailId("test@example.com");
        registerRequest.setPassword("password");

        when(userRepository.existsByEmailId(registerRequest.getEmailId())).thenReturn(false);

        // Call the method to be tested
        ResponseEntity<Object> response = registerController.create_account(registerRequest);

        // Assertions for successful registration
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof MessageResponse);

        MessageResponse messageResponse = (MessageResponse) responseBody;
        assertEquals("Registration is successful", messageResponse.getMessage());

        // Verify that save method was called in the repository
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testCreateAccount_ExistingUserRegistration() {
        // Prepare test data for an existing user registration
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmailId("existing@example.com");
        // Set other fields as needed...

        when(userRepository.existsByEmailId(registerRequest.getEmailId())).thenReturn(true);

        // Call the method to be tested
        ResponseEntity<Object> response = registerController.create_account(registerRequest);

        // Assertions for existing user registration
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof MessageResponse);

        MessageResponse messageResponse = (MessageResponse) responseBody;
        assertEquals("Error: Email is already in use!", messageResponse.getMessage());

        // Verify that save method was not called in the repository
        verify(userRepository, never()).save(any());
    }

    // Additional test cases can be added to cover more scenarios
}
