package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserDetailsImpl;

public class LoginTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	Authentication authentication;

	@Mock
	private JwtUtils jwtUtils;

	@InjectMocks
	private Login loginMock;

	@Mock
	PasswordEncoder encoder;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testLogin_ValidCredentials() {
		// Prepare test data for valid credentials scenario
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailId("test@example.com");
		loginRequest.setPassword("password");
		loginRequest.setUserType("USER");

		UserInfo userInfo = new UserInfo();
		userInfo.setEmailId(loginRequest.getEmailId());
		userInfo.setAccountStatus("ACTIVE");
		userInfo.setPassword(loginRequest.getPassword());
		userInfo.setLoginCounter(0L);
		userInfo.setUserType(loginRequest.getUserType());
		userInfo.setUserId(1L);

		// Build UserDetailsImpl
		UserDetailsImpl userDetails = UserDetailsImpl.build(userInfo);

		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(userRepository.existsByEmailId(loginRequest.getEmailId())).thenReturn(true);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication); // Mock successful authentication
		when(userRepository.findByEmailId(loginRequest.getEmailId())).thenReturn(userInfo);
		when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("test_token");
		// Call the method to be tested
		ResponseEntity<Object> response = loginMock.login(loginRequest);

		// Assertions for valid credentials scenario
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Object responseBody = response.getBody();
		assertNotNull(responseBody);
		assertTrue(responseBody instanceof JwtResponse);

		JwtResponse jwtResponse = (JwtResponse) responseBody;
		assertEquals("test_token", jwtResponse.getToken());
		// Add more assertions for other fields in JwtResponse
	}

	@Test
	public void testLogin_InactiveAccount() {
		// Prepare test data for inactive account scenario
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailId("inactive@example.com");
		loginRequest.setPassword("password");
		loginRequest.setUserType("USER");

		UserInfo userInfo = new UserInfo();
		userInfo.setEmailId(loginRequest.getEmailId());
		userInfo.setAccountStatus("INACTIVE");
		userInfo.setPassword(loginRequest.getPassword());
		userInfo.setLoginCounter(0L);
		userInfo.setUserType("ROLE_USER");
		userInfo.setUserId(1L);
		

		UserDetailsImpl userDetails = UserDetailsImpl.build(userInfo);

		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication); // Mock successful authentication
		when(userRepository.findByEmailId(loginRequest.getEmailId())).thenReturn(userInfo);

		// Call the method to be tested
		ResponseEntity<Object> response = loginMock.login(loginRequest);

		// Assertions for inactive account scenario
		assertNotNull(response);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		Object responseBody = response.getBody();
		assertNotNull(responseBody);
		// assertTrue(responseBody instanceof ResponseEntity);
		if (responseBody instanceof ResponseEntity) {
			ResponseEntity<?> apiResponse = (ResponseEntity<?>) responseBody;
			assertEquals(HttpStatus.FORBIDDEN, apiResponse.getStatusCode());
			// Add assertions for the message or status in the API response
		} else if (responseBody instanceof HashMap) {
			// Handle HashMap response for failure scenarios
			HashMap<?, ?> apiResponse = (HashMap<?, ?>) responseBody;
			assertEquals("User does not exist.", apiResponse.get("message"));
			// Add assertions or checks for the content of the HashMap
		} else {
			fail("Unexpected response type");
		}

	}

}
