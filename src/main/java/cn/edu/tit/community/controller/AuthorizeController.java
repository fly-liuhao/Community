package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.AccessTokenDTO;
import cn.edu.tit.community.dto.GithubUser;
import cn.edu.tit.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 授权登录：接收callback, 做access token
 */

@Controller
public class AuthorizeController {

    //    @Autowired
    @Resource
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    //    @RequestMapping("/callback")
    @GetMapping("/callback")
    public String callback(String code, String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getGithubUserInfo(accessToken);
        System.out.println(user);
        return "index";
    }
}
