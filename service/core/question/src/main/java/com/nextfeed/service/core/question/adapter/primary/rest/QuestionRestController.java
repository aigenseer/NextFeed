package com.nextfeed.service.core.question.adapter.primary.rest;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.service.external.dto.authorization.NewQuestionRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/question-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionRestController {

    private final IQuestionManager questionManager;
    private final ServiceUtils serviceUtils;

    @PostMapping("/v1/session/{sessionId}/question/create")
    public QuestionEntity createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(request.getParticipantId());
        var questionValue = questionManager.createQuestion(sessionId, request.getParticipantId(), request.getMessage(), System.currentTimeMillis(), request.getAnonymous());
        return questionValue.getEntity();
    }

}
