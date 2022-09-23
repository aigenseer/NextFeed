package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "question-repository-service-x", url = "${nextfeed.service.question-repository-service.domain}:${nextfeed.service.question-repository-service.port}", path = "/api/question-repository")
public interface QuestionRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Question save(@RequestBody Question question);

    @RequestMapping(value = "/v1/get/id/{questionId}", method = RequestMethod.GET)
    public Question findById(@PathVariable("questionId") Integer questionId);

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<Question> get(@PathVariable("sessionId") Integer sessionId);

}
