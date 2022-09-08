package com.nextfeed.service.external.session;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.SessionMetadata;
import com.nextfeed.library.core.service.external.QuestionService;
import com.nextfeed.library.core.service.external.SessionService;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/question-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController implements QuestionService {

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
