package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.dto.manager.question.NewQuestionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "question-manager-service")
@LoadBalancerClient(name = "question-manager-service", configuration = LoadBalancerConfiguration.class)
public interface QuestionManagerService {

    @RequestMapping(value = "/v1/question/{questionId}/exists", method = RequestMethod.GET)
    public Boolean existsQuestionId(@PathVariable("questionId") Integer questionId);

    @RequestMapping(value = "/v1/session/{sessionId}/question/create", method = RequestMethod.POST)
    public Question createQuestion(@PathVariable("sessionId") Integer sessionId, @RequestBody NewQuestionRequest request);

    @RequestMapping(value = "/v1/session/{sessionId}/question/{questionId}/participant/{participantId}", method = RequestMethod.PUT)
    public void ratingUpByQuestionId(@PathVariable("sessionId") Integer sessionId, @PathVariable("questionId") Integer questionId, @PathVariable("participantId") Integer participantId, @RequestBody Boolean rating);

    @RequestMapping(value = "/v1/session/{sessionId}/question/{questionId}/close", method = RequestMethod.POST)
    public void closeQuestion(@PathVariable("sessionId") Integer sessionId, @PathVariable("questionId") Integer questionId);

}
