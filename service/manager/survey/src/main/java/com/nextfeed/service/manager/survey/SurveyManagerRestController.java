package com.nextfeed.service.manager.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.SurveyManagerService;
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

import java.util.Collection;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/survey-manager")
public class SurveyManagerRestController implements SurveyManagerService {

    public static void main(String[] args) {
        SpringApplication.run(SurveyManagerRestController.class, args);
    }

    private final SurveyManager surveyManager;

    @RequestMapping(value = "/v1/session/{sessionId}/survey/create", method = RequestMethod.POST)
    public SurveyTemplate createSurvey(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyTemplate template) {
        return surveyManager.createSurvey(sessionId, template);
    }

    @RequestMapping(value = "/v1/survey/update", method = RequestMethod.PUT)
    public void updateSurvey(@RequestBody Survey survey) {
        surveyManager.updateSurvey(survey);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/surveys", method = RequestMethod.GET)
    public Collection<Survey> getSurveys(@PathVariable("sessionId") Integer sessionId) {
        return surveyManager.getSurveysBySessionId(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}/participant/{participantId}/answer", method = RequestMethod.PUT)
    public void addAnswerToSurvey(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId, @RequestBody String answer) {
        surveyManager.addAnswerToSurvey(sessionId, surveyId, participantId, answer);
    }

}
