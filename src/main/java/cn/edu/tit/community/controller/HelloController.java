package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.QuestionService;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试Controller控制器
 */

@Controller
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    /**
     * 测试方法
     */
    @GetMapping("/hello")
    public String hello(String name, Model model) {
        System.out.println(name);
        if (name != null && name.equals("")) {
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "Admin");
        }
        return "hello";
    }

    /**
     * index页面
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userService.findUserByToken(cookie.getValue());
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                    }
                }
            }
        }
        List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
        questionList = questionService.findAllQuestion();
        for (QuestionDTO q : questionList) {
            q.setDescription("hello Jack");
        }
        model.addAttribute("questions", questionList);

        return "index";
    }

    /**
     * 错误页面
     */
    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }
}
