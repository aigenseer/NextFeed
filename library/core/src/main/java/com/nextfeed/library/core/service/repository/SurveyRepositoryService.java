package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "survey-repository-service-x", url = "${nextfeed.service.survey-repository-service.domain}:${nextfeed.service.survey-repository-service.port}", path = "/api/survey-repository")
public interface SurveyRepositoryService {

    @RequestMapping(value = "/v1/survey/save", method = RequestMethod.POST)
    public Survey save(@RequestBody Survey survey);

    @RequestMapping(value = "/v1/survey/get/{surveyId}", method = RequestMethod.GET)
    public Survey findSurveyById(@PathVariable("surveyId") Integer surveyId);

    @RequestMapping(value = "/v1/survey-answer/save", method = RequestMethod.POST)
    public SurveyAnswer save(@RequestBody SurveyAnswer surveyAnswer);

    @RequestMapping(value = "/v1/survey-answer/exists/{surveyId}/{participantId}", method = RequestMethod.GET)
    public boolean existsSurveyAnswerByParticipant(@PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId);

    @RequestMapping(value = "/v1/survey-template/save", method = RequestMethod.POST)
    public SurveyTemplate save(@RequestBody SurveyTemplate surveyTemplate);

    @RequestMapping(value = "/v1/survey-template/all", method = RequestMethod.GET)
    public List<SurveyTemplate> findAllTemplates();

    @RequestMapping(value = "/v1/survey-template/id/{templateId}", method = RequestMethod.GET)
    public SurveyTemplate findTemplateById(@PathVariable("templateId") Integer templateId);

    @RequestMapping(value = "/v1/survey/all/{sessionId}", method = RequestMethod.GET)
    public List<Survey> findBySessionId(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/survey/delete/all/{sessionId}", method = RequestMethod.GET)
    public void deleteAllBySessionId(@PathVariable("sessionId") Integer sessionId);

}
