package cn.edu.tit.community.controller;

import cn.edu.tit.community.cache.TagCache;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.Question;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布功能的控制器
 */
@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    /**
     * 点击发布问题
     *
     * @return 发布问题页面
     */
    @GetMapping(value = "/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.getAllTag());
        return "publish";
    }

    /**
     * 发布或修改问题
     *
     * @param title       问题标题
     * @param description 问题描述
     * @param tag         问题标签
     * @param id          问题ID
     * @param request     请求
     * @param model       模型
     * @return 响应页面
     */
    @PostMapping(value = "/publish")
    public String doPublish(@RequestParam(name = "title") String title,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "tag") String tag,
                            @RequestParam(name = "id") Integer id,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.getAllTag());

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

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        // 添加或修改问题
        questionService.addOrModifyQuestion(question);

        return "redirect:/";
    }

    /**
     * 点击修改问题
     *
     * @param id    问题ID
     * @param model 模型
     * @return 跳转到发布页面
     */
    @GetMapping(value = "/publish/{id}")
    public String doRePublish(@PathVariable(name = "id") int id, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionByID(id);
        model.addAttribute("id", id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("tags", TagCache.getAllTag());

        return "publish";
    }

}
