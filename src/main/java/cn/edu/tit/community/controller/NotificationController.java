package cn.edu.tit.community.controller;

import cn.edu.tit.community.dto.NotificationDTO;
import cn.edu.tit.community.enums.NotificationTypeEnum;
import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        int questionId = notificationService.readNotification(id, user.getId());
        return "redirect:/question?id=" + questionId;
    }

}
