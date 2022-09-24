package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.question.QuestionDTO;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "question-repository-service-x", url = "${nextfeed.service.question-repository-service.domain}:${nextfeed.service.question-repository-service.port}", path = "/api/question-repository")
public interface QuestionRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public QuestionDTO save(@RequestBody QuestionEntity question);

    @RequestMapping(value = "/v1/get/id/{questionId}", method = RequestMethod.GET)
    public QuestionEntity findById(@PathVariable("questionId") Integer questionId);

    @RequestMapping(value = "/v1/get/dto/id/{questionId}", method = RequestMethod.GET)
    public QuestionDTO findDTOById(@PathVariable("questionId") Integer questionId);

    @RequestMapping(value = "/v1/question/{questionId}/vote/add/{participantId}", method = RequestMethod.POST)
    public void addVote(@PathVariable("questionId") Integer questionId, @PathVariable("participantId") Integer participantId, @RequestBody Integer rating);

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<QuestionDTO> findBySessionId(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/delete/all/{sessionId}", method = RequestMethod.GET)
    public void deleteAllBySessionId(@PathVariable("sessionId") Integer sessionId);

}
