package com.nextfeed.service.core.question.adapter.primary;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.grpc.service.manager.QuestionManagerServiceClient;
import com.nextfeed.library.core.service.external.dto.authorization.NewQuestionRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
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

    private final QuestionManagerServiceClient questionManagerServiceClient;
    private final ServiceUtils serviceUtils;

    @PostMapping("/v1/session/{sessionId}/question/create")
    public QuestionEntity createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(request.getParticipantId());
        var dto = questionManagerServiceClient.createQuestion(sessionId, request.getParticipantId(), request.getMessage(), System.currentTimeMillis(), request.getAnonymous());
        return DTO2EntityUtils.dto2Question(dto);
    }



}
