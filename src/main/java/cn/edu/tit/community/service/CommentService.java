package cn.edu.tit.community.service;

import cn.edu.tit.community.dto.CommentDTO;
import cn.edu.tit.community.model.Comment;

import java.util.List;

public interface CommentService {


    /**
     * 添加评论
     * @param comment 评论实体
     * @return 是否创建成功，成功返回true，失败返回false
     */
    public boolean addComment(Comment comment);

    /**
     * 获取评论
     * @param parentId
     * @param type
     * @return
     */
    public List<CommentDTO> findComment(int parentId, int type);
}
