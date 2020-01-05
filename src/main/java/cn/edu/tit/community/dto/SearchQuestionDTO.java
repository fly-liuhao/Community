package cn.edu.tit.community.dto;

import lombok.Data;

@Data
public class SearchQuestionDTO {
    // 分页的起始索引
    private Integer offset;
    // 分页时每页个数
    private Integer pageSize;
    // 问题的关键字
    private String keyword;

    public SearchQuestionDTO() {
    }

    public SearchQuestionDTO(Integer offset, Integer pageSize, String keyword) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.keyword = keyword.trim().replaceAll(" +", "|");
    }
}
