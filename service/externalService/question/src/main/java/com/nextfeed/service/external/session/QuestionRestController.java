package com.nextfeed.service.external.session;

import com.nextfeed.library.core.grpc.service.manager.QuestionManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.external.dto.authorization.NewQuestionRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
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
    public DTOEntities.QuestionDTO createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(request.getParticipantId());
        return questionManagerServiceClient.createQuestion(sessionId, request.getParticipantId(), request.getMessage(), request.getCreated(), request.getAnonymous());
    }



}
