package cn.edu.tit.community.provider;


import cn.edu.tit.community.dto.AccessTokenDTO;
import cn.edu.tit.community.dto.GithubUserDTO;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    /**
     * 获取用户的accessToken（用于后面获取github用户信息）
     *
     * @param accessTokenDTO 自定义封装的一些请求相关参数
     * @return String 类型的accessToken
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseStr = response.body().string();
            String access_token = responseStr.split("&")[0].split("=")[1];
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户的access Token获取geihub用户信息
     *
     * @param accessToken 获取到的accessToken
     * @return 自定的Github用户对象
     */
    public GithubUserDTO getGithubUserInfo(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
            // 通过fastJson将从网页获取到的用户信息，转化为用户的实体类
            GithubUserDTO githubUserDTO = JSON.parseObject(responseStr, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
