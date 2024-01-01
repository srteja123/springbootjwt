package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.AdminDashBoardResponse;
import com.example.demo.dto.DashboardResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(Dashboard.class)
public class DashboardTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@InjectMocks
	private Dashboard dashboard;

	@MockBean
	private JwtUtils jwtUtils;

	 @MockBean
	   AuthTokenFilter authFilter;

	@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
	@Test
	public void show_data() throws Exception {
		this.mockMvc.perform(get("/user/dashboard")).
		  andExpect(status().isUnauthorized());

	}

	 @Test
    public void testEditUser() {
        // Prepare test data
        UserRequest request = new UserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
		request.setEmailId("abc@gmail.com");
		request.setUserType("USER");
		request.setUserId(1L);
		request.setLoginCounter(2L);
		request.setPassword("jskgnr539853");
		request.setAccountStatus("ACTIVE");
        request.setCreatedTime(new java.util.Date());
        request.setUpdatedTime(new java.util.Date());

        Date presentTime = new Date();
        request.setUserId(request.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(request.getFirstName());
        userInfo.setLastName(request.getLastName());
		userInfo.setEmailId(request.getEmailId());
		userInfo.setCreatedTime(presentTime);
        userInfo.setUpdatedTime(presentTime);
		userInfo.setUserType(request.getUserType());
		userInfo.setLoginCounter(request.getLoginCounter());
		userInfo.setPassword(request.getPassword());
		userInfo.setAccountStatus(request.getAccountStatus());

        when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.editUser(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof UserInfo);

        UserInfo updatedUser = (UserInfo) responseBody;
        assertEquals(request.getFirstName(), updatedUser.getFirstName());
        assertEquals(request.getLastName(), updatedUser.getLastName());
         assertEquals(request.getCreatedTime(), updatedUser.getCreatedTime());
          assertEquals(request.getUpdatedTime(), updatedUser.getUpdatedTime());
        // Add assertions for other fields if needed

        verify(userRepository, times(1)).save(any(UserInfo.class));
    }

    @Test
    public void testEditUserException() {
        // Prepare test data
        UserRequest request = new UserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
		request.setEmailId("abc@gmail.com");
		request.setUserType("USER");
		request.setUserId(1L);

        when(userRepository.save(any(UserInfo.class))).thenThrow(new RuntimeException("Simulated exception"));

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.editUser(request);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof String);

        String errorMessage = (String) responseBody;
        assertTrue(errorMessage.contains("CATCH OUT"));
    }

	@Test
    public void testDeleteUser_Admin() {
        // Prepare test data for an ADMIN user
        UserRequest request = new UserRequest();
        request.setUserType("ADMIN");
        request.setUserId(1L); // Assuming the user ID is set

        List<UserInfo> userObjList = new ArrayList<>();
        // Add some user info objects to the list...

        when(userRepository.findAll()).thenReturn(userObjList);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.deleteUser(request);

        // Assertions for ADMIN user
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof List);

        List<?> returnedList = (List<?>) responseBody;
        assertEquals(userObjList.size(), returnedList.size());

        verify(userRepository, times(1)).deleteById(request.getUserId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUser_NonAdmin() {
        // Prepare test data for a non-ADMIN user
        UserRequest request = new UserRequest();
        request.setUserType("USER"); // Assuming a non-ADMIN user type

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.deleteUser(request);

        // Assertions for non-ADMIN user
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof String);

        String errorMessage = (String) responseBody;
        assertTrue(errorMessage.contains("User has no access"));
        
        // Verify that deleteById and findAll methods were not called for non-ADMIN user
        verify(userRepository, never()).deleteById(anyLong());
        verify(userRepository, never()).findAll();
    }

	 @Test
    public void testShowAdminData_ValidToken() {
        // Prepare test data for a valid token scenario
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        String validJwt = "valid_jwt_here";

        when(authFilter.parseJwt(mockRequest)).thenReturn(validJwt);
        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);

        String adminEmail = "admin@example.com";
        UserInfo adminUserObj = new UserInfo();
        adminUserObj.setEmailId(adminEmail);
        // Set other fields for adminUserObj...

        List<UserInfo> userObjList = new ArrayList<>();
        // Add some user info objects to the list...

        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn(adminEmail);
        when(userRepository.findByEmailId(adminEmail)).thenReturn(adminUserObj);
        when(userRepository.findAll()).thenReturn(userObjList);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.show_admindata(mockRequest);

        // Assertions for valid token scenario
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof AdminDashBoardResponse);

        AdminDashBoardResponse dashboardResponse = (AdminDashBoardResponse) responseBody;
        assertEquals(adminEmail, dashboardResponse.getEmail());
        // Add more assertions for other fields in AdminDashBoardResponse
    }

    @Test
    public void testShowAdminData_InvalidToken() {
        // Prepare test data for an invalid token scenario
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        String invalidJwt = "invalid_jwt_here";

        when(authFilter.parseJwt(mockRequest)).thenReturn(invalidJwt);
        when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.show_admindata(mockRequest);

        // Assertions for invalid token scenario
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof String);

        String errorMessage = (String) responseBody;
        assertTrue(errorMessage.contains("Token is not valid"));
    }

	@Test
    public void testShowData_ValidToken() {
        // Prepare test data for a valid token scenario
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        String validJwt = "valid_jwt_here";

        when(authFilter.parseJwt(mockRequest)).thenReturn(validJwt);
        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);

        String userEmail = "user@example.com";
        UserInfo userObj = new UserInfo();
        userObj.setEmailId(userEmail);
        // Set other fields for userObj...

        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn(userEmail);
        when(userRepository.findByEmailId(userEmail)).thenReturn(userObj);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.show_data(mockRequest);

        // Assertions for valid token scenario
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof DashboardResponse);

        DashboardResponse dashboardResponse = (DashboardResponse) responseBody;
        assertEquals(userEmail, dashboardResponse.getEmail());
        // Add more assertions for other fields in DashboardResponse
    }

    @Test
    public void testShowData_InvalidToken() {
        // Prepare test data for an invalid token scenario
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        String invalidJwt = "invalid_jwt_here";

        when(authFilter.parseJwt(mockRequest)).thenReturn(invalidJwt);
        when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

        // Call the method to be tested
        ResponseEntity<Object> response = dashboard.show_data(mockRequest);

        // Assertions for invalid token scenario
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof String);

        String errorMessage = (String) responseBody;
        assertTrue(errorMessage.contains("Token is not valid"));
    }
}

