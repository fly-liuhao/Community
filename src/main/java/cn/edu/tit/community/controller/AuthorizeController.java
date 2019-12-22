package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.AccessTokenDTO;
import cn.edu.tit.community.dto.GithubUserDTO;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.provider.GithubProvider;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 授权登录：接收callback, 做access token
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(String code, String state, HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);

        String accessToken = null;
        for (int i = 0; i < 10 && accessToken == null; i++) {
            accessToken = githubProvider.getAccessToken(accessTokenDTO);
            System.out.println("*-" + i);
        }
        GithubUserDTO githubUserDTO = null;
        if (accessToken != null) {
            for (int i = 0; i < 10 && githubUserDTO == null; i++) {
                githubUserDTO = githubProvider.getGithubUserInfo(accessToken);
                System.out.println("#-" + i);
            }
        }
        if (githubUserDTO != null) {
            // 将用户信息持久化到数据库当中
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            user.setBio(githubUserDTO.getBio());
            user.setAvatarUrl(githubUserDTO.getAvatarUrl());

            // 数据库添加用户或者更新用户信息
            userService.createOrUpdateUser(user);

            // 登录成功， 写 cookie
            Cookie cookie = new Cookie("token", token);
            // 向响应中添加Cookie
            response.addCookie(cookie);

        } else {
            System.out.println("accessToken: " + accessToken);
            System.out.println("githubUserDTO: " + githubUserDTO);
            System.out.println("登录失败！");
        }

        return "redirect:/";
    }
}
