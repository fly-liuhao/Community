package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.QuestionService;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String showMyQuestion(@PathVariable(name = "action") String action,
                                 HttpServletRequest request, Model model,
                                 @RequestParam(name = "currPage", defaultValue = "1") Integer currPage,
                                 @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        if("questions".equals(action)){
            User user = null;

            // 获取Cookie，如果找到key为token的cookie，该该token对应的用户添加到Session中，是的服务器重启时用户免登录
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        user = userService.findUserByToken(cookie.getValue());
                        if (user != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("user", user);
                            break;
                        }
                    }
                }
            }
            if(user == null) {
                return "redirect:/";
            }
            // 处理分页数据
            int totalCount = questionService.findQuestionCountByID(user.getId());
            int totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : totalCount / pageSize + 1;
            if (currPage < 1) {
                currPage = 1;
            }
            if (currPage > totalPage && totalPage != 0) {
                currPage = totalPage;
            }
            int offset = pageSize * (currPage - 1);

            // 查询该用户当前页的问题
            List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
            questionList = questionService.findQuestionByID(offset, pageSize, user.getId());
            // 获取用于分页的信息
            PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, totalPage);
            // 将数据添加到Model中去（用于前端使用）
            model.addAttribute("section", action);
            model.addAttribute("questions", questionList);
            model.addAttribute("pageInfo", pageInfoDTO);

            return "profile";
        }
        if("replies".equals(action)){
            List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
            PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, 0);

            model.addAttribute("section", action);
            model.addAttribute("questions", questionList);
            model.addAttribute("pageInfo", pageInfoDTO);
            return "profile";
        }
        if("draft".equals(action)){
            List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
            PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, 0);

            model.addAttribute("section", action);
            model.addAttribute("questions", questionList);
            model.addAttribute("pageInfo", pageInfoDTO);
            return "profile";
        }
        return "redirect:/";
    }

}
