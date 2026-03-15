package com.wyh.ssm.blog.service;

import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.exception.ResourceNotFoundException;
import com.wyh.ssm.blog.exception.ValidationException;
import com.wyh.ssm.blog.mapper.ArticleMapper;
import com.wyh.ssm.blog.mapper.UserMapper;
import com.wyh.ssm.blog.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * UserService 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 测试")
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(1)
                .userName("testuser")
                .userPass("e10adc3949ba59abbe56e057f20f883e")
                .userNickname("测试用户")
                .userEmail("test@example.com")
                .userAvatar("/img/avatar.png")
                .userStatus(1)
                .userRegisterTime(new Date())
                .build();
    }

    @Test
    @DisplayName("根据 ID 查询用户 - 成功")
    void getUserById_Success() {
        when(userMapper.getUserById(1)).thenReturn(testUser);

        User result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("testuser", result.getUserName());
        verify(userMapper, times(1)).getUserById(1);
    }

    @Test
    @DisplayName("根据 ID 查询用户 - 用户不存在")
    void getUserById_NotFound() {
        when(userMapper.getUserById(999)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999));
    }

    @Test
    @DisplayName("根据 ID 查询用户 - ID 为空")
    void getUserById_NullId() {
        assertThrows(ValidationException.class, () -> userService.getUserById(null));
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void listUser_Success() {
        List<User> users = new ArrayList<>();
        users.add(testUser);
        users.add(User.builder().userId(2).userName("user2").userNickname("用户 2").build());

        when(userMapper.listUser()).thenReturn(users);
        when(articleMapper.countArticleByUser(anyInt())).thenReturn(5);

        List<User> result = userService.listUser();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userMapper, times(1)).listUser();
    }

    @Test
    @DisplayName("创建用户 - 成功")
    void insertUser_Success() {
        when(userMapper.getUserByName("testuser")).thenReturn(null);
        when(userMapper.getUserByEmail("test@example.com")).thenReturn(null);
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUserId(100);
            return null;
        }).when(userMapper).insert(any(User.class));

        User result = userService.insertUser(testUser);

        assertNotNull(result);
        verify(userMapper, times(1)).insert(testUser);
    }

    @Test
    @DisplayName("创建用户 - 用户名已存在")
    void insertUser_UsernameExists() {
        when(userMapper.getUserByName("testuser")).thenReturn(testUser);

        assertThrows(ValidationException.class, () -> userService.insertUser(testUser));
    }

    @Test
    @DisplayName("创建用户 - 邮箱已存在")
    void insertUser_EmailExists() {
        when(userMapper.getUserByName("testuser")).thenReturn(null);
        when(userMapper.getUserByEmail("test@example.com")).thenReturn(testUser);

        assertThrows(ValidationException.class, () -> userService.insertUser(testUser));
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void updateUser_Success() {
        testUser.setUserNickname("新昵称");
        userService.updateUser(testUser);
        verify(userMapper, times(1)).update(testUser);
    }

    @Test
    @DisplayName("更新用户 - 用户信息为空")
    void updateUser_NullUser() {
        assertThrows(ValidationException.class, () -> userService.updateUser(null));
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        when(articleMapper.countArticleByUser(1)).thenReturn(0);

        userService.deleteUser(1);

        verify(userMapper, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("删除用户 - 有文章无法删除")
    void deleteUser_HasArticles() {
        when(articleMapper.countArticleByUser(1)).thenReturn(5);

        assertThrows(ValidationException.class, () -> userService.deleteUser(1));
    }

    @Test
    @DisplayName("根据用户名查询用户 - 成功")
    void getUserByName_Success() {
        when(userMapper.getUserByName("testuser")).thenReturn(testUser);

        User result = userService.getUserByName("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUserName());
    }

    @Test
    @DisplayName("根据用户名查询用户 - 用户名为空")
    void getUserByName_EmptyName() {
        assertThrows(ValidationException.class, () -> userService.getUserByName(""));
    }

    @Test
    @DisplayName("根据用户名或邮箱查询 - 成功")
    void getUserByNameOrEmail_Success() {
        when(userMapper.getUserByNameOrEmail("testuser")).thenReturn(testUser);

        User result = userService.getUserByNameOrEmail("testuser");

        assertNotNull(result);
        verify(userMapper, times(1)).getUserByNameOrEmail("testuser");
    }

    @Test
    @DisplayName("根据用户名或邮箱查询 - 输入为空")
    void getUserByNameOrEmail_EmptyInput() {
        assertThrows(ValidationException.class, () -> userService.getUserByNameOrEmail(""));
    }
}
