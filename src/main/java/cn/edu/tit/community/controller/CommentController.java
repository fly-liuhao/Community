package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.CommentDto;
import cn.edu.tit.community.dto.ResponseDTO;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.edu.tit.community.model.Comment;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    @CrossOrigin
    public Object comment(@RequestBody CommentDto commentDto,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResponseDTO.errorOf(new CustomizeException(CustomizeErrorCodeEnum.NO_LOGIN));
        }
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setParentId(commentDto.getParentId());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0L);

        // 添加评论
        commentService.addComment(comment);

        return ResponseDTO.okOf();
    }

}
