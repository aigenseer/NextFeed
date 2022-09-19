package com.nextfeed.service.external.session;


import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/question-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController {

    public static void main(String[] args) {
        SpringApplication.run(QuestionRestController.class, args);
    }

    private final QuestionManagerService questionManagerService;
    private final ServiceUtils serviceUtils;

    @PostMapping("/v1/session/{sessionId}/question/create")
    public Question createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(request.getParticipantId());
        return questionManagerService.createQuestion(sessionId, request);
    }

}
