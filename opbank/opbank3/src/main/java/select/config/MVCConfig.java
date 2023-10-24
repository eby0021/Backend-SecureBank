package select.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import select.util.AuthenticationInterceptor;

/**
 * Project: OP-Bank
 * Module ID:
 * Comments:
 * JDK version used: JDK 17
 * Namespace: std
 * Author： YeTian
 * Create Date： 9/10/2023
 * Modified By： None
 * Modified Date: None
 * Why & What is modified: None
 * Version: 0.1.0
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor authenticationInterceptor(){
        return new AuthenticationInterceptor() ;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/") ;
    }

    @Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor())
            .addPathPatterns("/secure/*") // Adjust the path pattern as needed
            .excludePathPatterns("/sys/user/loginCheck")
            .excludePathPatterns("/sys/user/login")
            .excludePathPatterns("/sys/user/insertOne"); 

}

}
