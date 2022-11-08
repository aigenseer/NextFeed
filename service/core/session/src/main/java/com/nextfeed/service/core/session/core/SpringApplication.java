package com.nextfeed.service.core.session.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan("com.nextfeed.library.core.entity.session")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

}
