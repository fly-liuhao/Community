package cn.edu.tit.community.advice;

import cn.edu.tit.community.exception.CustomizeErrorCode;
import cn.edu.tit.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e) {

        HttpStatus status = getStatus(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");

        if(e instanceof CustomizeException){
            modelAndView.addObject("message", e.getMessage());
        } else {
            modelAndView.addObject("message", CustomizeErrorCode.SYS_ERROR.getMessage());
        }

        return modelAndView;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
