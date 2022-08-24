package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "survey-socket-service")
@LoadBalancerClient(name = "survey-socket-service")
public interface SurveySocketService {

    @RequestMapping(value = "/v1/socket/session/{sessionId}/survey/admin", method = RequestMethod.POST)
    public void onCreateByAdmin(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey);

    @RequestMapping(value = "/v1/socket/session/{sessionId}/survey/{surveyId}", method = RequestMethod.POST)
    public void onCreateByParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId, @RequestBody SurveyTemplate template);

    @RequestMapping(value = "/v1/socket/session/{sessionId}/survey/{surveyId}/close", method = RequestMethod.GET)
    public void onClose(@PathVariable("sessionId") Integer sessionId, @PathVariable("surveyId") Integer surveyId);

    @RequestMapping(value = "/v1/socket/session/{sessionId}/survey/update", method = RequestMethod.POST)
    public void onUpdate(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey);

    @RequestMapping(value = "/v1/socket/session/{sessionId}/survey/result", method = RequestMethod.POST)
    public void onResult(@PathVariable("sessionId") Integer sessionId, @RequestBody Survey survey);

}
