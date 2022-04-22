package com.github.xini1;

import com.github.xini1.application.SpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Maxim Tereshchenko
 */
@Configuration
@EnableAutoConfiguration
@Import(SpringConfiguration.class)
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
