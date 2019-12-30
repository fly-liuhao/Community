package cn.edu.tit.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 用于前端添加标签的实体类
 */
@Data
public class TagDTO {
    // 种类名称
    private String categoryName;
    // 种类下的标签集合
    private List<String> tags;
}