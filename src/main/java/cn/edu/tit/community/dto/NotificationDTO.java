package cn.edu.tit.community.dto;


import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String typeInfo;
    private String outerTitle;
}
