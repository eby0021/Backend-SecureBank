package select;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "select.system.dto")
public class SelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectApplication.class, args);
    }

}
