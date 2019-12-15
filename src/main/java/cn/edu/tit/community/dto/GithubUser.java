package cn.edu.tit.community.dto;

import lombok.Data;

/**
 * 使用accesstoken获取到的Github用户信息实体类
 */
@Data
public class GithubUser {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
}
