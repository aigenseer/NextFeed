package com.nextfeed.service.manager.question;


import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;



@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/question-manager")
public class QuestionManagerRestController implements QuestionManagerService {

    public static void main(String[] args) {
        SpringApplication.run(QuestionManagerRestController.class, args);
    }

    private final QuestionManager questionManager;

    @RequestMapping(value = "/v1/question/{questionId}/exists", method = RequestMethod.GET)
    public Boolean existsQuestionId(@PathVariable("questionId") Integer questionId) {
        return questionManager.existsQuestionId(questionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/question/create", method = RequestMethod.POST)
    public Question createQuestion(@PathVariable("sessionId") Integer sessionId, @RequestBody NewQuestionRequest request) {
        return questionManager.createQuestion(sessionId, request);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/question/{questionId}/participant/{participantId}", method = RequestMethod.PUT)
    public void ratingUpByQuestionId(@PathVariable("sessionId") Integer sessionId, @PathVariable("questionId") Integer questionId, @PathVariable("participantId") Integer participantId, @RequestBody Boolean rating) {
        questionManager.ratingUpByQuestionId(sessionId, questionId, participantId, rating);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/question/{questionId}/close", method = RequestMethod.POST)
    public void closeQuestion(@PathVariable("sessionId") Integer sessionId, @PathVariable("questionId") Integer questionId) {
        questionManager.closeQuestion(sessionId, questionId);
    }

}
