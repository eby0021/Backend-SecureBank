package select;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "select.system.dto")
@MapperScan("select.system.dao") // Define the package where your mappers are located
public class SelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectApplication.class, args);
    }

}
