package com.nextfeed.service.socket.question;


import com.nextfeed.library.core.grpc.service.manager.QuestionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.security.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@Controller
public class QuestionSocketController {

    public static void main(String[] args) {
        SpringApplication.run(QuestionSocketController.class, args);
    }

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final QuestionManagerServiceClient questionManagerServiceClient;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/question-socket/v1/participant/session/{sessionId}/question/{questionId}/rating/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, @DestinationVariable String rating, Principal principal){
        Optional<Integer> userId = principalUtils.getClaim("id", principal);
        if(userId.isPresent() && sessionManagerServiceClient.existsSessionId(sessionId) && questionManagerServiceClient.existsQuestionId(questionId)){
            boolean r = false;
            switch (rating){
                case "up" -> r = true;
                case "down" -> r = false;
            }
            questionManagerServiceClient.ratingUpByQuestionId(sessionId, questionId, userId.get(), r);
        }
    }

    @MessageMapping("/socket/question-socket/v1/presenter/session/{sessionId}/question/{questionId}/close")
    public void closeQuestion(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId){
        if(sessionManagerServiceClient.existsSessionId(sessionId) && questionManagerServiceClient.existsQuestionId(questionId)){
            questionManagerServiceClient.closeQuestion(sessionId, questionId);
        }
    }


}
