package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "survey-manager-service-x", url = "${nextfeed.service.survey-manager-service.domain}:${nextfeed.service.survey-manager-service.port}", path = "/api/survey-manager")
public interface SurveyManagerService {

    @RequestMapping(value = "/v1/session/{sessionId}/survey/create", method = RequestMethod.POST)
    SurveyTemplate createSurvey(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyTemplate template);

    @RequestMapping(value = "/v1/session/{sessionId}/surveys", method = RequestMethod.GET)
    List<SurveyDTO> getSurveys(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/survey/{surveyId}/participant/{participantId}/answer", method = RequestMethod.PUT)
    void addAnswerToSurvey(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @PathVariable("participantId") Integer participantId, @RequestBody String answer);

}
