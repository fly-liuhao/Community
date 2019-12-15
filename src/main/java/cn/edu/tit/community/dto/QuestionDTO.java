package cn.edu.tit.community.dto;

import cn.edu.tit.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer commentCount = 0;
    private Integer viewCount = 0;
    private Integer likeCount = 0;
    private Long gmtCreate;
    private Long gmtModify;
    private Long creator;
    User user;
}
