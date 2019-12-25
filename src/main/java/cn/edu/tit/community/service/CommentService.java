package cn.edu.tit.community.service;

import cn.edu.tit.community.model.Comment;

public interface CommentService {


    /**
     * 添加评论
     * @param comment 评论实体
     * @return 是否创建成功，成功返回true，失败返回false
     */
    public boolean addComment(Comment comment);
}
