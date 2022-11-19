package com.nextfeed.service.core.question.core.socket;

import com.nextfeed.library.core.valueobject.question.QuestionValue;
import com.nextfeed.service.core.question.ports.incoming.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class QuestionService implements IQuestionService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/socket/question-socket/v1/%s/session/%d/question/onupdate";
    private static final String[] roots = {"presenter","participant"};

    public void sendQuestion(int sessionId, QuestionValue question){
        Arrays.stream(roots).
        map(root -> WS_MESSAGE_TRANSFER_DESTINATION.formatted(root, sessionId))
        .forEach( path -> simpMessagingTemplate.convertAndSend(path, question.getEntity()));
    }
}
