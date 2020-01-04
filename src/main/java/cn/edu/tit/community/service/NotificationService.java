package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.NotificationDTO;
import cn.edu.tit.community.model.Notification;

import java.util.List;

/**
 * 消息通知接口
 */
public interface NotificationService {

    /**
     * 添加消息通知
     *
     * @param notification 通知实体
     * @return 成功返回true, 失败返回false
     */
    public boolean addNotification(Notification notification);

    /**
     * 查询用户未读通知
     *
     * @param receiver 接收者ID
     * @return 用于传输的通知集合
     */
    List<NotificationDTO> findNotificationByUser(int offset, int pageSize, int receiver);

    /**
     * 查询用户所有的通知个数
     *
     * @param receiver 接收者ID
     * @return 该用户所有通知个数
     */
    int findNotificationCountByUser(Integer receiver);

    /**
     * 查询用户未读通知个数
     *
     * @param receiver 接收者ID
     * @return 该用户未读通知个数
     */
    int findNotificationCountByUserUnRead(Integer receiver);


    /**
     * 阅读通知，返回所属问题的ID
     *
     * @param id     通知ID
     * @param userId 用户ID
     * @return 该通知所属问题的ID
     */
    public Integer readNotification(Long id, Integer userId);
}
