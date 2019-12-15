package cn.edu.tit.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String token;
    private String accountId;
    private Long gmtCreate;
    private Long gmtModify;
    private String bio;
    private String avatarUrl;
}
