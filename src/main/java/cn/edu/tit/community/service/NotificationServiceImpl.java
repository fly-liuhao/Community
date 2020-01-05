package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.NotificationDTO;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.enums.NotificationStatusEnum;
import cn.edu.tit.community.enums.NotificationTypeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.edu.tit.community.mapper.CommentMapper;
import cn.edu.tit.community.mapper.NotificationMapper;
import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;


    @Override
    public boolean addNotification(Notification notification) {
        if (notification.getNotifier().equals(notification.getReceiver())) {
            return true;
        }
        int insertResult = notificationMapper.insert(notification);
        return insertResult != 0 ? true : false;
    }

    @Override
    public List<NotificationDTO> findNotificationByUser(int offset, int pageSize, int receiver) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiver);
        example.setOrderByClause("status asc, gmt_create desc");// 从大到小排列--按时间倒序排列
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        if (notifications.isEmpty()) {
            return notificationDTOS;
        }

        // 获取去重的评论人ID
        Set<Integer> notifiers = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList();
        userIds.addAll(notifiers);

        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 notification 为 notificationDTO
        notificationDTOS = notifications.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotifierName(userMap.get(notification.getNotifier()).getName());
            notificationDTO.setTypeInfo(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTO.setOuterTitle(getTitle(notification.getOuterId(), notification.getType()));
            return notificationDTO;
        }).collect(Collectors.toList());

        return notificationDTOS;
    }

    @Override
    public int findNotificationCountByUser(Integer receiver) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiver);
        return (int) notificationMapper.countByExample(example);
    }

    @Override
    public int findNotificationCountByUserUnRead(Integer receiver) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiver)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return (int) notificationMapper.countByExample(example);
    }

    @Override
    public Integer readNotification(Long id, Integer userId) {
        // 获取指定ID的通知
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCodeEnum.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), userId)) {
            throw new CustomizeException(CustomizeErrorCodeEnum.READ_NOTIFICATION_FAIL);
        }

        // 将通知的状态设置为已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        // 返回通知所属问题的ID
        if (notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
            return commentMapper.selectByPrimaryKey(notification.getOuterId()).getParentId();
        } else {
            return notification.getOuterId();
        }
    }

    public String getTitle(int otherId, int type) {
        String title = "";
        if (type == NotificationTypeEnum.REPLY_COMMENT.getType()) {
            title = commentMapper.selectByPrimaryKey(otherId).getContent();
        } else {
            title = questionMapper.selectByPrimaryKey(otherId).getTitle();
        }
        if (title.length() > 50) {
            title = title.substring(0, 50) + "...";
        }
        return title;
    }

    /**
     * 获取问题的ID
     *
     * @param otherId 评论或者问题ID
     * @param type    评论类型
     * @return 评论所属问题的ID
     */
    public Integer getQuestionID(int otherId, int type) {
        Integer questionId = null;
        if (type == NotificationTypeEnum.REPLY_COMMENT.getType()) {
            questionId = commentMapper.selectByPrimaryKey(otherId).getParentId();
        } else {
            return otherId;
        }
        return questionId;
    }
}


