package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("=== ВСЕ ЗАРЕГИСТРИРОВАННЫЕ МАРШРУТЫ ===");
            ctx.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((request, handler) -> {
                    System.out.println(request.getPatternsCondition() + " - " 
                        + request.getMethodsCondition().getMethods());
                });
        };
    }
}