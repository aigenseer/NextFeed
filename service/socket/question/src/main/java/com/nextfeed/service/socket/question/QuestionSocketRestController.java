package com.nextfeed.service.socket.question;

import com.nextfeed.library.core.entity.question.QuestionDTO;
import com.nextfeed.library.core.service.socket.QuestionSocketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/internal/question-socket")
public class QuestionSocketRestController implements QuestionSocketService {

    private final QuestionService questionService;

    @RequestMapping(value = "/v1/session/{sessionId}/question", method = RequestMethod.POST)
    public void sendQuestion(@PathVariable("sessionId") Integer sessionId, @RequestBody QuestionDTO question){
        questionService.sendQuestion(sessionId, question);
    }

}




