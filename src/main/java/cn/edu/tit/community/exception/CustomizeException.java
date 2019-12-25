package cn.edu.tit.community.exception;

import cn.edu.tit.community.enums.ICustomizeErrorCodeEnum;
import lombok.Data;

@Data
public class CustomizeException extends RuntimeException {
    Integer code;
    String message;

    public CustomizeException(ICustomizeErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
