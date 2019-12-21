package cn.edu.tit.community.interceptor;

import cn.edu.tit.community.model.User;
import cn.edu.tit.community.service.QuestionService;
import cn.edu.tit.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SesstionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    // 在进入控制器之前执行
    // 如果返回值为false，阻止进入控制器
    // 控制代码（什么请求可以进控制器，什么不可以进）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Cookie，如果找到key为token的cookie，该token对应的用户添加到Session中，使得服务器重启时用户免登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userService.findUserByToken(cookie.getValue());
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        break;
                    }
                }
            }
        }
        return true;
    }

    // 控制器执行完成，进入到jsp页面之前执行
    // 日志记录
    // 敏感词语过滤
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // jsp页面执行完成之后执行
    // 记录在执行过程中出现的异常（有没有异常都执行）
    // 可以把异常记录到日志中
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
