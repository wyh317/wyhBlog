package com.wyh.ssm.blog.controller.admin;

import com.wyh.ssm.blog.controller.BaseController;
import com.wyh.ssm.blog.dto.ApiResponse;
import com.wyh.ssm.blog.dto.request.LoginRequest;
import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.service.UserService;
import com.wyh.ssm.blog.util.MyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.wyh.ssm.blog.util.MyUtils.getIpAddr;

/**
 * 后台管理 REST Controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController extends BaseController {

    private final UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<User> login(
            @Validated @RequestBody LoginRequest request,
            @RequestParam(required = false) Boolean rememberMe,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {

        User user = userService.getUserByNameOrEmail(request.getUsername());
        if (user == null) {
            return ApiResponse.unauthorized("用户名或密码错误");
        }

        // 密码 MD5 验证
        String md5Password = MyUtils.strToMd5(request.getPassword());
        if (!user.getUserPass().equals(md5Password)) {
            return ApiResponse.unauthorized("用户名或密码错误");
        }

        // 登录成功，设置 session
        HttpSession session = httpRequest.getSession();
        session.setAttribute("user", user);

        // 记住我功能
        if (Boolean.TRUE.equals(rememberMe)) {
            Cookie usernameCookie = new Cookie("username", request.getUsername());
            usernameCookie.setMaxAge(60 * 60 * 24 * 3); // 3 天
            Cookie passwordCookie = new Cookie("password", md5Password);
            passwordCookie.setMaxAge(60 * 60 * 24 * 3);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
        }

        // 更新登录信息
        user.setUserLastLoginTime(new Date());
        user.setUserLastLoginIp(getIpAddr(httpRequest));
        userService.updateUser(user);

        log.info("用户登录成功，username: {}", user.getUserName());

        // 不返回密码
        user.setUserPass(null);
        return ApiResponse.success("登录成功", user);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        log.info("用户登出成功");
        return ApiResponse.success("登出成功", null);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ApiResponse.unauthorized("未登录");
        }
        // 不返回密码
        user.setUserPass(null);
        return ApiResponse.success(user);
    }

    /**
     * 检查登录状态
     */
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> checkLoginStatus(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return ApiResponse.success(user != null);
    }
}
