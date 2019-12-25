package cn.edu.tit.community.enums;

public interface ICustomizeErrorCodeEnum {

    /**
     * 获取错误异常代号
     *
     * @return 异常代号
     */
    Integer getCode();

    /**
     * 获取错误异常信息
     *
     * @return 异常信息
     */
    String getMessage();
}
