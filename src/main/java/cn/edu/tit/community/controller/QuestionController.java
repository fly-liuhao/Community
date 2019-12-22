package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question")
    public String question(@RequestParam("id") int id, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionByID(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
