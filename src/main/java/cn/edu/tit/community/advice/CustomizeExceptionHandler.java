package cn.edu.tit.community.advice;

import cn.edu.tit.community.dto.ResponseDTO;
import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            // 返回 JSON
            ResponseDTO responseDTO;
            if (e instanceof CustomizeException) {
                // 如果是自定义的异常
                responseDTO = ResponseDTO.errorOf((CustomizeException) e);
            } else {
                // 不是自定义的异常，返回一个自定义的服务器端异常
                responseDTO = ResponseDTO.errorOf(new CustomizeException(CustomizeErrorCodeEnum.SYS_ERROR));
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(responseDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        } else {
            // 错误页面跳转
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            if (e instanceof CustomizeException) {
                modelAndView.addObject("message", e.getMessage());
            } else {
                modelAndView.addObject("message", CustomizeErrorCodeEnum.SYS_ERROR.getMessage());
            }
            return modelAndView;
        }
    }

    /**
     * 获取请求的状态码
     * @param request 用户请求
     * @return 状态码
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
