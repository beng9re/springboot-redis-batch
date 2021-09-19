package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan("chat")
})
public class WebApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
