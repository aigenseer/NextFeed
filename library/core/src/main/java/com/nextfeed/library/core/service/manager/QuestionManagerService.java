package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "question-manager-service-x", url = "${nextfeed.service.question-manager-service.domain}:${nextfeed.service.question-manager-service.port}", path = "/api/question-manager")
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
