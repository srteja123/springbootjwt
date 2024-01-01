package com.example.demo.security.jwt;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthTokenFilter() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = User.withUsername("testUser@gmail.com")
        .password("password")
        .roles("USER")
        .build();
        String token = "Bearer YOUR_TOKEN_HERE";
        // Mocking JWT validation and user details retrieval
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtils.validateJwtToken(anyString())).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenReturn("testUser@gmail.com");
        when(userDetailsService.loadUserByUsername("testUser@gmail.com")).thenReturn(userDetails);

        // Creating a test token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Perform filter operation
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that the authentication was set in SecurityContextHolder
       verify(userDetailsService, times(1)).loadUserByUsername("testUser@gmail.com");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    // Add more test cases to cover different scenarios (e.g., invalid tokens, no token, etc.)
}
