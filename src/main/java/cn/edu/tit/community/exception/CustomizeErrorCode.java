package cn.edu.tit.community.exception;

import lombok.Data;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找的问题已经不在了，要不换一个试试？"),
    SYS_ERROR("服务冒烟了，要不然你稍后再试试！！！");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
