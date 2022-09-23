package com.nextfeed.service.manager.survey;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.SurveyManagerService;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
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

    @RequestMapping(value = "/v1/session/{sessionId}/surveys", method = RequestMethod.GET)
    public List<SurveyDTO> getSurveys(@PathVariable("sessionId") Integer sessionId) {
        return surveyManager.getSurveysBySessionId(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}/participant/{participantId}/answer", method = RequestMethod.PUT)
    public void addAnswerToSurvey(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId, @RequestBody String answer) {
        surveyManager.addAnswerToSurvey(sessionId, surveyId, participantId, answer);
    }

}
