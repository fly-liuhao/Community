package cn.edu.tit.community.controller;

import cn.edu.tit.community.mapper.QuestionMapper;
import cn.edu.tit.community.mapper.UserMapper;
import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.QuestionService;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 发布功能的控制器
 */
@Controller
public class PublishController {

    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping(value = "/publish")
    public String doPublish(@RequestParam(name = "title") String title,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title.equals("")) {
            model.addAttribute("error", "title不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "description不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "tag不能为空");
            return "publish";
        }


        User user = null;
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    if (token != null) {
                        user = userService.findUserByToken(token);
                    }
                    break;
                }
            }
        }

        System.out.println("Access Question Controller...");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModify(question.getGmtCreate());
        questionService.addQuestion(question);

        return "redirect:/";
    }

}
