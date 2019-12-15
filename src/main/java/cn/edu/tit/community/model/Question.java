package cn.edu.tit.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer commentCount = 0;
    private Integer viewCount = 0;
    private Integer likeCount = 0;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
}
