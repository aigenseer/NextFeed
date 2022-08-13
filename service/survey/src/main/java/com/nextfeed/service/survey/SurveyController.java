package com.nextfeed.service.survey;


import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.request.NewSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RefreshScope
@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@ServletComponentScan("com.nextfeed")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/survey", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyController implements SurveyService {

    public static void main(String[] args) {
        SpringApplication.run(SurveyController.class, args);
    }

    private final SessionManagerService sessionManagerService;
    private final String uniqueID = UUID.randomUUID().toString();

    @Value("${preix.attribute}")
    private String password;

    @GetMapping("/test")
    public TestRequest create() {
        var session = sessionManagerService.createEntity(new NewSession("TestName"));
        System.out.println(session.getName());
        System.out.println("yes geht " +uniqueID+ "password: "+password);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return new TestRequest("Test");
    }

}
