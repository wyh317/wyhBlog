package com.wyh.ssm.blog.controller.admin;

import com.wyh.ssm.blog.dto.ApiResponse;
import com.wyh.ssm.blog.entity.Article;
import com.wyh.ssm.blog.entity.Comment;
import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.exception.BusinessException;
import com.wyh.ssm.blog.service.ArticleService;
import com.wyh.ssm.blog.service.CommentService;
import com.wyh.ssm.blog.service.UserService;
import com.wyh.ssm.blog.util.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 后台管理控制器
 */
@Controller
public class AdminController {
    
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private CommentService commentService;
    
    /**
     * 后台首页
     */
    @RequestMapping("/admin")
    public String index(Model model) {
        List<Article> articleList = articleService.listRecentArticle(5);
        model.addAttribute("articleList", articleList);
        
        List<Comment> commentList = commentService.listRecentComment(5);
        model.addAttribute("commentList", commentList);
        
        return "Admin/index";
    }
    
    /**
     * 登录页面
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "Admin/login";
    }
    
    /**
     * 登录验证
     */
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ApiResponse<User> loginVerify(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "rememberme", required = false) String rememberme,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            return ApiResponse.error(400, "用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return ApiResponse.error(400, "密码不能为空");
        }
        
        // 查询用户
        User user = userService.getUserByNameOrEmail(username.trim());
        if (user == null) {
            return ApiResponse.error(401, "用户名或密码错误");
        }
        
        // 验证密码
        if (!user.getUserPass().equals(password)) {
            return ApiResponse.error(401, "用户名或密码错误");
        }
        
        // 登录成功，添加 session
        request.getSession().setAttribute("user", user);
        
        // 添加 cookie（记住密码）
        if (rememberme != null && "1".equals(rememberme)) {
            Cookie nameCookie = new Cookie("username", username.trim());
            nameCookie.setMaxAge(60 * 60 * 24 * 3); // 3 天
            nameCookie.setPath("/");
            nameCookie.setHttpOnly(true);
            
            Cookie pwdCookie = new Cookie("password", password);
            pwdCookie.setMaxAge(60 * 60 * 24 * 3);
            pwdCookie.setPath("/");
            pwdCookie.setHttpOnly(true);
            
            response.addCookie(nameCookie);
            response.addCookie(pwdCookie);
            log.info("用户 [{}] 勾选了记住密码", username);
        }
        
        // 更新登录信息
        user.setUserLastLoginTime(new Date());
        user.setUserLastLoginIp(MyUtils.getIpAddr(request));
        userService.updateUser(user);
        
        log.info("用户 [{}] 登录成功", username);
        return ApiResponse.success("登录成功", user);
    }
    
    /**
     * 退出登录
     */
    @RequestMapping(value = "/admin/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            log.info("用户 [{}] 退出登录", user.getUserName());
        }
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";
    }
}
