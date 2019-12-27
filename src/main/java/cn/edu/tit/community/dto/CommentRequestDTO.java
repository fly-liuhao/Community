package cn.edu.tit.community.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private Integer parentId;
    private String content;
    private Integer type;
}
