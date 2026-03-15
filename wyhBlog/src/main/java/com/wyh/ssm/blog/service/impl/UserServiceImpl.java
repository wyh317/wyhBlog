package com.wyh.ssm.blog.service.impl;

import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.exception.ResourceNotFoundException;
import com.wyh.ssm.blog.exception.ValidationException;
import com.wyh.ssm.blog.mapper.ArticleMapper;
import com.wyh.ssm.blog.mapper.UserMapper;
import com.wyh.ssm.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    @Override
    public List<User> listUser() {
        List<User> userList = userMapper.listUser();
        userList.forEach(user -> {
            Integer articleCount = articleMapper.countArticleByUser(user.getUserId());
            user.setArticleCount(articleCount);
        });
        return userList;
    }

    @Override
    public User getUserById(Integer id) {
        if (id == null) {
            throw new ValidationException("用户 ID 不能为空");
        }
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户", id.longValue());
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        if (user == null || user.getUserId() == null) {
            throw new ValidationException("用户信息不能为空");
        }
        userMapper.update(user);
        log.info("用户信息已更新，userId: {}", user.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        if (id == null) {
            throw new ValidationException("用户 ID 不能为空");
        }
        // 检查用户是否有文章
        Integer articleCount = articleMapper.countArticleByUser(id);
        if (articleCount > 0) {
            throw new ValidationException("该用户下有 " + articleCount + " 篇文章，无法删除");
        }
        userMapper.deleteById(id);
        log.info("用户已删除，userId: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insertUser(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.getUserByName(user.getUserName());
        if (existingUser != null) {
            throw new ValidationException("用户名已存在");
        }
        // 检查邮箱是否已存在
        if (user.getUserEmail() != null) {
            existingUser = userMapper.getUserByEmail(user.getUserEmail());
            if (existingUser != null) {
                throw new ValidationException("邮箱已被注册");
            }
        }
        user.setUserRegisterTime(new Date());
        userMapper.insert(user);
        log.info("用户已注册，userId: {}", user.getUserId());
        return user;
    }

    @Override
    public User getUserByNameOrEmail(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new ValidationException("用户名或邮箱不能为空");
        }
        return userMapper.getUserByNameOrEmail(str);
    }

    @Override
    public User getUserByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("用户名不能为空");
        }
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("邮箱不能为空");
        }
        return userMapper.getUserByEmail(email);
    }
}
