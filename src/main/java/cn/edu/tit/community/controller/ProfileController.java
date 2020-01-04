package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.NotificationDTO;
import cn.edu.tit.community.dto.PageInfoDTO;
import cn.edu.tit.community.dto.QuestionDTO;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.NotificationService;
import cn.edu.tit.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String showMyQuestion(HttpServletRequest request, Model model,
                                 @PathVariable(name = "action") String action,
                                 @RequestParam(name = "currPage", defaultValue = "1") Integer currPage,
                                 @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        // 获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            // 处理分页数据
            int totalCount = questionService.findQuestionCountByCreator(user.getId());
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
            questionList = questionService.findQuestionByCreator(offset, pageSize, user.getId());
            // 获取用于分页的信息
            PageInfoDTO pageInfoDTO = questionService.getPageInfo(currPage, totalPage);
            // 将数据添加到Model中去（用于前端使用）
            model.addAttribute("section", action);
            model.addAttribute("questions", questionList);
            model.addAttribute("pageInfo", pageInfoDTO);

            return "profile";
        }
        if ("replies".equals(action)) {
            // 处理分页数据
            int totalCount = notificationService.findNotificationCountByUser(user.getId());
            int totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : totalCount / pageSize + 1;
            if (currPage < 1) {
                currPage = 1;
            }
            if (currPage > totalPage && totalPage != 0) {
                currPage = totalPage;
            }
            int offset = pageSize * (currPage - 1);


            List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();
            notificationDTOS = notificationService.findNotificationByUser(offset, pageSize, user.getId());
            PageInfoDTO pageInfoDTO = new PageInfoDTO(currPage, totalPage);

            model.addAttribute("section", action);
            model.addAttribute("notifications", notificationDTOS);
            model.addAttribute("pageInfo", pageInfoDTO);
            return "profile";
        }
        if ("draft".equals(action)) {
            List<QuestionDTO> questionList = new ArrayList<QuestionDTO>();
            PageInfoDTO pageInfoDTO = new PageInfoDTO(currPage, pageSize);

            model.addAttribute("section", action);
            model.addAttribute("questions", questionList);
            model.addAttribute("pageInfo", pageInfoDTO);
            return "profile";
        }
        return "redirect:/";
    }

}
