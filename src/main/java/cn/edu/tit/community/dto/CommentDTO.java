package cn.edu.tit.community.dto;

import cn.edu.tit.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Integer id;
    private Integer parentId;
    private Integer type;
    private String content;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer likeCount;
    private Integer commentCount;
    private String pubtime;
    private  User user;
}
