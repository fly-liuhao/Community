package cn.edu.tit.community.dto;

import lombok.Data;

/**
 * 用于富文本上传图片传输的DTO
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}
