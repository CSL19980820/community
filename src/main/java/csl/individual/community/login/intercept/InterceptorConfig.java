package csl.individual.community.login.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器,拦截未登录用户的部分页面的访问
 * 放行静态资源
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( loginHandlerInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/","/callback**","/js/**","/html/**","/image/**","/css/**","img/**","/register",
                "/layUi/**","/getEmail","/send/mail","/contrastCode");
    }

}
