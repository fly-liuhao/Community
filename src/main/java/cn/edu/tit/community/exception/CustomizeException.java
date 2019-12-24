package cn.edu.tit.community.exception;

import lombok.Data;

@Data
public class CustomizeException extends RuntimeException {
    String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
