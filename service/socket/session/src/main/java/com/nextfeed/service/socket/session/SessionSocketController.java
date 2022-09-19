package com.nextfeed.service.socket.session;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@Controller
public class SessionSocketController {

    public static void main(String[] args) {
        SpringApplication.run(SessionSocketController.class, args);
    }

}
