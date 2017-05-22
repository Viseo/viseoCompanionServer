package com.viseo.companion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@SpringBootApplication
public class ViseocompanionserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViseocompanionserverApplication.class, args);
    }
}
