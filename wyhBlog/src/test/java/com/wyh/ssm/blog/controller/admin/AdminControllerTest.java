package com.wyh.ssm.blog.controller.admin;

import com.wyh.ssm.blog.dto.ApiResponse;
import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminController 单元测试
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private AdminController adminController;
    
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    
    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        request.setSession(session);
    }
    
    @Test
    public void loginVerify_WithEmptyUsername_ShouldReturnError() {
        // Given
        String emptyUsername = "";
        String password = "123456";
        
        // When
        ApiResponse<User> result = adminController.loginVerify(
            emptyUsername, password, null, request, response);
        
        // Then
        assertThat(result.getCode()).isEqualTo(400);
        assertThat(result.getMessage()).contains("用户名不能为空");
    }
    
    @Test
    public void loginVerify_WithEmptyPassword_ShouldReturnError() {
        // Given
        String username = "admin";
        String emptyPassword = "";
        
        // When
        ApiResponse<User> result = adminController.loginVerify(
            username, emptyPassword, null, request, response);
        
        // Then
        assertThat(result.getCode()).isEqualTo(400);
        assertThat(result.getMessage()).contains("密码不能为空");
    }
    
    @Test
    public void loginVerify_WithNonExistentUser_ShouldReturnError() {
        // Given
        String username = "nonexistent";
        String password = "123456";
        when(userService.getUserByNameOrEmail(username)).thenReturn(null);
        
        // When
        ApiResponse<User> result = adminController.loginVerify(
            username, password, null, request, response);
        
        // Then
        assertThat(result.getCode()).isEqualTo(401);
        assertThat(result.getMessage()).contains("用户名或密码错误");
    }
    
    @Test
    public void loginVerify_WithWrongPassword_ShouldReturnError() {
        // Given
        String username = "admin";
        String wrongPassword = "wrongpassword";
        User user = new User();
        user.setUserName(username);
        user.setUserPass("123456");
        when(userService.getUserByNameOrEmail(username)).thenReturn(user);
        
        // When
        ApiResponse<User> result = adminController.loginVerify(
            username, wrongPassword, null, request, response);
        
        // Then
        assertThat(result.getCode()).isEqualTo(401);
        assertThat(result.getMessage()).contains("用户名或密码错误");
    }
    
    @Test
    public void loginVerify_WithValidCredentials_ShouldReturnSuccess() {
        // Given
        String username = "admin";
        String password = "123456";
        User user = new User();
        user.setUserName(username);
        user.setUserPass(password);
        when(userService.getUserByNameOrEmail(username)).thenReturn(user);
        doNothing().when(userService).updateUser(any(User.class));
        
        // When
        ApiResponse<User> result = adminController.loginVerify(
            username, password, null, request, response);
        
        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMessage()).isEqualTo("登录成功");
        verify(userService).updateUser(any(User.class));
    }
}
