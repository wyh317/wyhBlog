package com.wyh.ssm.blog.service;

import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.mapper.UserMapper;
import com.wyh.ssm.blog.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UserService 单元测试
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    private User testUser;
    
    @Before
    public void setUp() {
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUserName("admin");
        testUser.setUserPass("123456");
        testUser.setUserNickname("管理员");
        testUser.setUserEmail("admin@example.com");
        testUser.setUserStatus(1);
        testUser.setUserRegisterTime(new Date());
    }
    
    @Test
    public void getUserById_WithValidId_ShouldReturnUser() {
        // Given
        when(userMapper.getUserById(1)).thenReturn(testUser);
        
        // When
        User result = userService.getUserById(1);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getUserName()).isEqualTo("admin");
        verify(userMapper).getUserById(1);
    }
    
    @Test
    public void getUserById_WithInvalidId_ShouldReturnNull() {
        // Given
        when(userMapper.getUserById(999)).thenReturn(null);
        
        // When
        User result = userService.getUserById(999);
        
        // Then
        assertThat(result).isNull();
        verify(userMapper).getUserById(999);
    }
    
    @Test
    public void getUserByNameOrEmail_WithValidUsername_ShouldReturnUser() {
        // Given
        when(userMapper.getUserByNameOrEmail("admin")).thenReturn(testUser);
        
        // When
        User result = userService.getUserByNameOrEmail("admin");
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo("admin");
        verify(userMapper).getUserByNameOrEmail("admin");
    }
    
    @Test
    public void getUserByNameOrEmail_WithValidEmail_ShouldReturnUser() {
        // Given
        when(userMapper.getUserByNameOrEmail("admin@example.com")).thenReturn(testUser);
        
        // When
        User result = userService.getUserByNameOrEmail("admin@example.com");
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserEmail()).isEqualTo("admin@example.com");
        verify(userMapper).getUserByNameOrEmail("admin@example.com");
    }
    
    @Test
    public void updateUser_WithValidUser_ShouldCallMapper() {
        // Given
        doNothing().when(userMapper).update(testUser);
        
        // When
        userService.updateUser(testUser);
        
        // Then
        verify(userMapper).update(testUser);
    }
    
    @Test
    public void listUser_ShouldReturnUserList() {
        // Given
        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        when(userMapper.listUser()).thenReturn(userList);
        
        // When
        List<User> result = userService.listUser();
        
        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserName()).isEqualTo("admin");
        verify(userMapper).listUser();
    }
    
    @Test
    public void countUser_ShouldReturnCount() {
        // Given
        when(userMapper.countUser()).thenReturn(5L);
        
        // When
        Long result = userService.countUser();
        
        // Then
        assertThat(result).isEqualTo(5L);
        verify(userMapper).countUser();
    }
}
