package cn.edu.tit.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc //会使得你的静态资源以及exclude的全部失效！！！！
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    SesstionInterceptor sesstionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sesstionInterceptor).addPathPatterns("/**");
    }

}