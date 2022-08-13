package com.nextfeed.service.user;


import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.request.NewSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

//    private final SurveyService surveyService;
    private final SessionManagerService sessionManagerService;

    @GetMapping("/create")
    public UserRequest create() {
        var session = sessionManagerService.createEntity(NewSession.builder().name("Test").build());
        System.out.printf("yes %s", session.getName());
        return new UserRequest("TestUser");
    }



}
