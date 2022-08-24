package com.nextfeed.service.socket.question;


import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.socket.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RequiredArgsConstructor
@Controller
public class QuestionSocketController {

    public static void main(String[] args) {
        SpringApplication.run(QuestionSocketController.class, args);
    }

    private final SessionManagerService sessionManagerService;
    private final QuestionManagerService questionManagerService;

    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, @DestinationVariable String rating, Principal principal){
        int userId = PrincipalUtils.getClaim("id", principal).asInt();
        if(sessionManagerService.existsSessionId(sessionId) && questionManagerService.existsQuestionId(questionId)){
            boolean r = false;
            switch (rating){
                case "up" -> r = true;
                case "down" -> r = false;
            }
            questionManagerService.ratingUpByQuestionId(sessionId, questionId, userId, r);
        }
    }

    @MessageMapping("/admin/session/{sessionId}/question/{questionId}/close")
    public void closeQuestion(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId){
        if(sessionManagerService.existsSessionId(sessionId) && questionManagerService.existsQuestionId(questionId)){
            questionManagerService.closeQuestion(sessionId, questionId);
        }
    }


}
