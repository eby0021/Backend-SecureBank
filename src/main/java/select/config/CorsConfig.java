package select.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * @author Frank
 * @date 15/11/2023
 * @time 12:07 pm
 */
@Configuration

public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");  // 允许任何域
        config.addAllowedHeader("*");  // 允许任何头
        config.addAllowedMethod("*");  // 允许任何方法
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
