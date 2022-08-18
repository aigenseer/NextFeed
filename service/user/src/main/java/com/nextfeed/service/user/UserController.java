package com.nextfeed.service.user;


import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.entities.NewSessionCreate;
import com.nextfeed.library.core.service.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {


    private final SessionManagerService sessionManagerService;

//    @GetMapping("/create")
//    public UserRequest create() throws ExecutionException, InterruptedException {
//        var r = sessionManagerService.getObjectA(Student.builder().name("hallo2").grade("2").registrationNumber("3").build());
////        var session = sessionManagerService.createEntity(NewSession.builder().name("Test").build());
////        System.out.printf("yes %s", session.getName());
//        return new UserRequest("TestUser");
//    }


    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @GetMapping("/test")
    public UserRequest test() throws ExecutionException, InterruptedException {
        System.out.println(groupId+" "+bootstrapServers);
        sessionManagerService.createSession(NewSessionCreate.builder().name("MySession").build());
        return new UserRequest("TestUser");
    }



}
