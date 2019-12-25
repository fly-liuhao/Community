package cn.edu.tit.community.service;

import cn.edu.tit.community.enums.CommentTypeEnum;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.edu.tit.community.mapper.CommentExtMapper;
import cn.edu.tit.community.mapper.CommentMapper;
import cn.edu.tit.community.mapper.QuestionExtMapper;
import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.model.Comment;
import cn.edu.tit.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Override
    @Transactional
    public boolean addComment(Comment comment) {

        // 该问题不存在
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TARGET_PARAM_NOT_FOUND);
        }
        // 类型不存在
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCodeEnum.TYPE_PARAM_WRONG);
        }

        // 添加评论（回复问题|回复评论）
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }

            // 添加评论
            int insertResult = commentMapper.insert(comment);
            // 增加问题评论数
            int incResult = questionExtMapper.incCommentCount(comment.getParentId(), 1);
            return insertResult != 0 && incResult != 0 ? true : false;
        } else {

            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
            }

            // 添加评论
            int insertResult = commentMapper.insert(comment);
            // 增加问题评论发回复数
            int incResult = commentExtMapper.incCommentCount(comment.getParentId(), 1);
            return insertResult != 0 && incResult != 0 ? true : false;
        }
    }
}
