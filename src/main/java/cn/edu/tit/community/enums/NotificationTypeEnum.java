package cn.edu.tit.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了你的问题"),
    REPLY_COMMENT(2, "回复了你的评论");
    private int type;
    private String info;


    public int getType() {
        return type;
    }

    public String getName() {
        return info;
    }

    NotificationTypeEnum(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
