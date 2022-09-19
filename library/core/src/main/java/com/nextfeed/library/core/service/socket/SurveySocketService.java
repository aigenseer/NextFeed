package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.service.manager.dto.survey.SurveyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "survey-socket-service")
public interface SurveySocketService {

    @RequestMapping(value = "/api/internal/survey-socket/v1/session/{sessionId}/survey/presenter", method = RequestMethod.POST)
    void onCreateByPresenter(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO);

    @RequestMapping(value = "/api/internal/survey-socket/v1/session/{sessionId}/survey/{surveyId}", method = RequestMethod.POST)
    void onCreateByParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @RequestBody SurveyTemplate template);

    @RequestMapping(value = "/api/internal/survey-socket/v1/session/{sessionId}/survey/{surveyId}/close", method = RequestMethod.GET)
    void onClose(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId);

    @RequestMapping(value = "/api/internal/survey-socket/v1/session/{sessionId}/survey/update", method = RequestMethod.POST)
    void onUpdate(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO);

    @RequestMapping(value = "/api/internal/survey-socket/v1/session/{sessionId}/survey/result", method = RequestMethod.POST)
    void onResult(@PathVariable("sessionId") Integer sessionId, @RequestBody SurveyDTO surveyDTO);

}
