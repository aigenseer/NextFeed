package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.service.repository.QuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/question-repository")
public class QuestionRepositoryRestController implements QuestionRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(QuestionRepositoryRestController.class, args);
    }

    private final QuestionDBService questionDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Question save(@RequestBody Question question) {
        return questionDBService.save(question);
    }

    @RequestMapping(value = "/v1/get/id/{questionId}", method = RequestMethod.GET)
    public Question findById(@PathVariable("questionId") Integer questionId) {
        return questionDBService.findById(questionId);
    }

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<Question> get(@PathVariable("sessionId") Integer sessionId) {
        return List.of();
    }

}
