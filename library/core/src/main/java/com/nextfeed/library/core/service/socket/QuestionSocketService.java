package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.question.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "question-socket-service")
public interface QuestionSocketService {

    @RequestMapping(value = "/api/internal/question-socket/v1/session/{sessionId}/question", method = RequestMethod.POST)
    public void sendQuestion(@PathVariable("sessionId") Integer sessionId, @RequestBody Question question);
}
