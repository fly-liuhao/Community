package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.CommentDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.Comment;
import cn.edu.tit.community.service.CommentService;
import cn.edu.tit.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question")
    public String question(@RequestParam("id") int id, Model model) {
        // 获取问题的详细信息
        QuestionDTO questionDTO = questionService.getQuestionByID(id);
        if(questionDTO != null){
            questionService.incViewCount(questionDTO.getId(),1);
            questionDTO.setViewCount(questionDTO.getViewCount()+1);
        }
        // 获取问题的回复
        List<CommentDTO> commentDTO = commentService.findComment(id,1);

        // 传输到前端页面
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTO);
        return "question";
    }
}
