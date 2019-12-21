package cn.edu.tit.community.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String token;
    private String accountId;
    private Long gmtCreate;
    private Long gmtModify;
    private String bio;
    private String avatarUrl;
}
