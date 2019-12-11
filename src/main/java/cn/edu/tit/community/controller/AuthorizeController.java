package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.AccessTokenDTO;
import cn.edu.tit.community.dto.GithubUser;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.provider.GithubProvider;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 授权登录：接收callback, 做access token
 */

@Controller
public class AuthorizeController {

    //    @Autowired
    @Resource
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    //    @RequestMapping("/callback")
    @GetMapping("/callback")
    public String callback(String code, String state, HttpServletRequest request, HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUserInfo(accessToken);
        if (githubUser != null) {
            // 将用户信息持久化到数据库当中
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            System.out.println(user);
            userService.addUser(user);

            // 登录成功， 写 cookie 和 session
            HttpSession session = request.getSession();
            session.setAttribute("user", githubUser);
            // 创建两个cookie，分别用于存放用户名以及密码
            Cookie cookie = new Cookie("userName", githubUser.getName());
            cookie.setMaxAge(60);// 设置Cookie有效期为10天
            // 向响应中添加Cookie
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            // 登录失败，重新登录
            return "redirect:/";
        }
    }
}
