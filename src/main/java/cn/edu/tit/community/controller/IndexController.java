package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.service.QuestionService;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Controller控制器
 */

@Controller
public class IndexController {

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
    public String index(Model model,
                        @RequestParam(name = "currPage", defaultValue = "1") Integer currPage,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

        // 处理分页数据
        int totalCount = questionService.findQuestionCount();
        int totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : totalCount / pageSize + 1;
        if (currPage < 1) {
            currPage = 1;
        }
        if (currPage > totalPage && totalPage != 0) {
            currPage = totalPage;
        }
        int offset = pageSize * (currPage - 1);

        // 查询当前页的问题
        List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
        questionList = questionService.findQuestion(offset, pageSize);
        // 获取用于分页的信息
        PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, totalPage);
        // 将数据添加到Model中去（用于前端使用）
        model.addAttribute("questions", questionList);
        model.addAttribute("pageInfo", pageInfoDTO);

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
