package com.nextfeed.service.core.question.adapter.primary.socket;

import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.security.utils.PrincipalUtils;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class QuestionSocketController {

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final IQuestionManager questionManager;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/question-socket/v1/participant/session/{sessionId}/question/{questionId}/rating/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, @DestinationVariable String rating, Principal principal){
        Optional<Integer> userId = principalUtils.getClaim("id", principal);
        if(userId.isPresent() && sessionManagerServiceClient.existsSessionId(sessionId) && questionManager.existsQuestionId(questionId)){
            boolean r = false;
            switch (rating){
                case "up" -> r = true;
                case "down" -> r = false;
            }
            questionManager.ratingUpByQuestionId(sessionId, questionId, userId.get(), r);
        }
    }

    @MessageMapping("/socket/question-socket/v1/presenter/session/{sessionId}/question/{questionId}/close")
    public void closeQuestion(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId){
        if(sessionManagerServiceClient.existsSessionId(sessionId) && questionManager.existsQuestionId(questionId)){
            questionManager.closeQuestion(sessionId, questionId);
        }
    }


}
