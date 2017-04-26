package com.viseo.companion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableAutoConfiguration
@Configuration
@EnableJpaRepositories
@ComponentScan({ "com.viseo.companion.service", "com.viseo.companion.controller"})
@EntityScan("com.viseo.companion.domain")
@SpringBootApplication
public class ViseocompanionserverApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {

		SpringApplication.run(ViseocompanionserverApplication.class, args);
	}

	@Override
	public final void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/client/").addResourceLocations("classpath:/.tmp/");
	}
}
