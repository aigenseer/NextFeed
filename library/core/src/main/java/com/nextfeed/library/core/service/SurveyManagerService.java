package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.dto.manager.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.dto.manager.mood.NewMoodRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@FeignClient(name = "survey-manager-service")
@LoadBalancerClient(name = "survey-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SurveyManagerService {

    @RequestMapping(value = "/v1/session/{sessionId}/survey/create", method = RequestMethod.POST)
    public SurveyTemplate createSurvey(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyTemplate template);

    @RequestMapping(value = "/v1/survey/update", method = RequestMethod.PATCH)
    public void updateSurvey(@RequestBody Survey survey);

    @RequestMapping(value = "/v1/session/{sessionId}/surveys", method = RequestMethod.GET)
    public Collection<Survey> getSurveys(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}/participant/{participantId}/answer", method = RequestMethod.PUT)
    public void addAnswerToSurvey(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId, @RequestBody String answer);

}
