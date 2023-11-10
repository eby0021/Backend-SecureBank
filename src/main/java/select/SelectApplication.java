package select;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = "select.system.dto")
@MapperScan("select.system.dao") // Define the package where your mappers are located
public class SelectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectApplication.class, args);
    }

}

@RestController
public class RootController {
    @RequestMapping("/")
    public String root() {
        return "Hello there!! I am at azure";
    }
}
