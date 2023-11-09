package select.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import select.util.LogCostFilter;

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
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean() ;
        registration.setFilter(new LogCostFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logCostFilter");
        registration.setOrder(1);
        return registration;
    }
}
