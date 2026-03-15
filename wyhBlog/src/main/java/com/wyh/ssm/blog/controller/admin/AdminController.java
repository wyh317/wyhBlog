package com.wyh.ssm.blog.controller.admin;

import com.wyh.ssm.blog.entity.Article;
import com.wyh.ssm.blog.entity.Comment;
import com.wyh.ssm.blog.entity.User;
import com.wyh.ssm.blog.service.ArticleService;
import com.wyh.ssm.blog.service.CommentService;
import com.wyh.ssm.blog.service.UserService;
import com.wyh.ssm.blog.util.MyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wyh.ssm.blog.util.MyUtils.getIpAddr;

/**
 * 后台管理 Controller（JSP 页面兼容）
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;

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
     * 登录页面显示
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "Admin/login";
    }

    /**
     * 登录验证（修复 MD5 密码验证问题）
     */
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String loginVerify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(4);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberme = request.getParameter("rememberme");

        User user = userService.getUserByNameOrEmail(username);
        if (user == null) {
            map.put("code", 0);
            map.put("msg", "用户名无效！");
        } else {
            // 修复：对输入的密码进行 MD5 加密后再比较
            String md5Password = MyUtils.strToMd5(password);
            if (!user.getUserPass().equals(md5Password)) {
                map.put("code", 0);
                map.put("msg", "密码错误！");
            } else {
                // 登录成功
                map.put("code", 1);
                map.put("msg", "");

                // 添加 session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // 添加 cookie（记住我功能）
                if (rememberme != null) {
                    Cookie nameCookie = new Cookie("username", username);
                    nameCookie.setMaxAge(60 * 60 * 24 * 3);
                    Cookie pwdCookie = new Cookie("password", md5Password);
                    pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                    response.addCookie(nameCookie);
                    response.addCookie(pwdCookie);
                }

                // 更新登录信息
                user.setUserLastLoginTime(new Date());
                user.setUserLastLoginIp(getIpAddr(request));
                userService.updateUser(user);

                log.info("用户登录成功，username: {}", user.getUserName());
            }
        }

        return new JSONObject(map).toString();
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        log.info("用户退出登录");
        return "redirect:/login";
    }
}
