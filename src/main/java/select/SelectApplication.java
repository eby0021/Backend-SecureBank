package select;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan(basePackages = "select.system.dto")
@MapperScan("select.system.dao") // Define the package where your mappers are located
@ComponentScan(basePackages = "select.**")
public class SelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectApplication.class, args);
    }

}
