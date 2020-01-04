package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.service.NotificationService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试Controller控制器
 */

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    /**
     * index页面
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "currPage", defaultValue = "1") Integer currPage,
                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

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
        List<QuestionDTO> questionList = questionService.findQuestion(offset, pageSize);
        // 获取用于分页的信息
        PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, totalPage);
        // 将数据添加到Model中去（用于前端使用）
        model.addAttribute("questions", questionList);
        model.addAttribute("pageInfo", pageInfoDTO);

        return "index";
    }


    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(name = "keyword") String keyword,
                         @RequestParam(name = "currPage", defaultValue = "1") Integer currPage,
                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        // 处理分页数据
        int totalCount = questionService.findQuestionCountByTitle(keyword);
        int totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : totalCount / pageSize + 1;
        if (currPage < 1) {
            currPage = 1;
        }
        if (currPage > totalPage && totalPage != 0) {
            currPage = totalPage;
        }
        int offset = pageSize * (currPage - 1);

        // 查询当前页的问题
        List<QuestionDTO> questionList = questionService.findQuestionByTitle(offset, pageSize, keyword);
        // 获取用于分页的信息
        PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, totalPage);
        // 将数据添加到Model中去（用于前端使用）
        model.addAttribute("questions", questionList);
        model.addAttribute("pageInfo", pageInfoDTO);

        return "index";
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        // 清除Session
        request.getSession().removeAttribute("user");
        // 清除Cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // 重定向到首页
        return "redirect:/";
    }
}
