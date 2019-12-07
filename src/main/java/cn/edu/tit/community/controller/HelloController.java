package cn.edu.tit.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试Controller控制器
 */

@Controller
public class HelloController {

    /**
     * 测试方法
     */
    @RequestMapping("/hello")
    public String hello(String name, Model model){
        System.out.println(name);
        if(name!=null&&name.equals("")){
            model.addAttribute("name",name);
        }else{
            model.addAttribute("name","Admin");
        }
        return "hello";
    }

    /**
     * 测试index页面
     */
    @RequestMapping("/")
    public String index(){
//        System.out.println("进入index.html页面~~");
        return "index";
    }
}
