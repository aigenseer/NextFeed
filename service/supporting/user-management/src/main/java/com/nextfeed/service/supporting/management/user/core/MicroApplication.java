package com.nextfeed.service.supporting.management.user.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan({"com.nextfeed.library.core.entity.participant", "com.nextfeed.library.core.entity.user"})
@EnableJpaRepositories(basePackages = "com.nextfeed.service.supporting.management.user.ports.outgoing")
@SpringBootApplication(scanBasePackages = "com.nextfeed")
public class MicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroApplication.class, args);
    }

}
