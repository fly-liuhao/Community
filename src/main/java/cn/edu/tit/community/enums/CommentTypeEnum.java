package cn.edu.tit.community.enums;

/**
 * 问题回复类型的(枚举)
 *
 * QUESTION：问题的回复
 * COMMENT：评论的回复
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 判断是否存在该枚举值
     * @param type 待判断枚举值
     * @return 存在返回true，反之返回false
     */
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
