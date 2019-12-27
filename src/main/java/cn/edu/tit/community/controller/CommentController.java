package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.CommentRequestDTO;
import cn.edu.tit.community.dto.ResponseDTO;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.edu.tit.community.model.Comment;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    @CrossOrigin
    public Object comment(@RequestBody CommentRequestDTO commentRequestDTO,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResponseDTO.errorOf(new CustomizeException(CustomizeErrorCodeEnum.NO_LOGIN));
        }
        // 判断使用了apache提供的工具包
        if(commentRequestDTO.getContent() == null || StringUtils.isBlank(commentRequestDTO.getContent())){
            return ResponseDTO.errorOf(new CustomizeException(CustomizeErrorCodeEnum.CONTENT_IS_EMPTY));
        }
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        comment.setParentId(commentRequestDTO.getParentId());
        comment.setType(commentRequestDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0);

        // 添加评论
        commentService.addComment(comment);

        return ResponseDTO.okOf();
    }

}
