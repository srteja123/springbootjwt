package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

public class UserDetailsImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
    @Test
    public void testBuildUserDetailsImpl() {
        // Prepare test data
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setEmailId("test@example.com");
        userInfo.setPassword("password");
        userInfo.setLoginCounter(0L);
        userInfo.setAccountStatus("ACTIVE");
        userInfo.setUserType("ROLE_USER");

        // Build UserDetailsImpl
        UserDetailsImpl userDetails = UserDetailsImpl.build(userInfo);

        // Assertions
        assertNotNull(userDetails);
        assertEquals(1L, userDetails.getId());
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
        assertEquals(0L, userDetails.getLoginCounter());
        assertEquals("ACTIVE", userDetails.getAccountStatus());

        // Authorities (role)
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testUserDetailsImplMethods() {
        // Prepare test data
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                "test@example.com",
                "password",
                0L,
                "ACTIVE",
                authorities
        );

        // Assertions for UserDetailsImpl methods
        assertEquals(1L, userDetails.getId());
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
        assertEquals(0L, userDetails.getLoginCounter());
        assertEquals("ACTIVE", userDetails.getAccountStatus());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals(authorities, userDetails.getAuthorities());
    }
}
