package com.nextfeed.library.core.service.external;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.external.dto.survey.MessageRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@FeignClient(name = "survey-service")
@LoadBalancerClient(name = "survey-service", configuration = LoadBalancerConfiguration.class)
public interface SurveyService {

    @GetMapping("/api/survey-service/v1/admin/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId);

    @GetMapping("/api/survey-service/v1/admin/session/{sessionId}/surveys")
    public Collection<Survey> getSessionSurveys(@PathVariable int sessionId);

    @GetMapping("/api/survey-service/v1/admin/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId);

    @PostMapping("/api/survey-service/v1/admin/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template);

    @PostMapping("/api/survey-service/v1/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageRequest request, @RequestHeader("Authorization") String token);

}
