package com.nextfeed.service.repository.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.repository.SurveyRepositoryService;
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
@RequestMapping(value = "/api/survey-repository")
public class SurveyRepositoryRestController implements SurveyRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(SurveyRepositoryRestController.class, args);
    }

    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;
    private final SurveyTemplateDBService surveyTemplateDBService;

    @RequestMapping(value = "/v1/survey/save", method = RequestMethod.POST)
    public Survey save(@RequestBody Survey survey) {
        return surveyDBService.save(survey);
    }

    @RequestMapping(value = "/v1/survey/get/{surveyId}", method = RequestMethod.GET)
    public Survey findSurveyById(@PathVariable("surveyId") Integer surveyId) {
        return surveyDBService.findById(surveyId);
    }

    @RequestMapping(value = "/v1/survey-answer/save", method = RequestMethod.POST)
    public SurveyAnswer save(@RequestBody SurveyAnswer surveyAnswer) {
        return surveyAnswerDBService.save(surveyAnswer);
    }

    @RequestMapping(value = "/v1/survey-answer/exists/{surveyId}/{participantId}", method = RequestMethod.GET)
    public boolean existsSurveyAnswerByParticipant(@PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId) {
        return surveyAnswerDBService.existsSurveyAnswerByParticipant(participantId, surveyId);
    }

    @RequestMapping(value = "/v1/survey-template/save", method = RequestMethod.POST)
    public SurveyTemplate save(@RequestBody SurveyTemplate surveyTemplate) {
        return surveyTemplateDBService.save(surveyTemplate);
    }

    @RequestMapping(value = "/v1/survey-template/all", method = RequestMethod.GET)
    public List<SurveyTemplate> findAllTemplates() {
        return surveyTemplateDBService.findAll();
    }

    @RequestMapping(value = "/v1/survey-template/id/{templateId}", method = RequestMethod.GET)
    public SurveyTemplate findTemplateById(@PathVariable("templateId") Integer templateId) {
        return surveyTemplateDBService.findById(templateId);
    }

    @RequestMapping(value = "/v1/survey/all/{sessionId}", method = RequestMethod.GET)
    public List<Survey> findAllBySessionId(@PathVariable("sessionId") Integer sessionId) {
        return List.of();
    }

}
