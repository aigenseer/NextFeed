package com.nextfeed.library.core.service.external;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.SessionMetadata;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "question-service")
@LoadBalancerClient(name = "question-service", configuration = LoadBalancerConfiguration.class)
public interface QuestionService {

    @PostMapping("/api/session-service/v1/{sessionId}/question/create")
    public Question createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId);

}
