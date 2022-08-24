package com.nextfeed.service.socket.question;

import com.nextfeed.library.core.entity.Question;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class QuestionService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String WS_MESSAGE_TRANSFER_DESTINATION = "/%s/session/%d/question/onupdate";
    private final String[] roots = {"admin","participant"};

    QuestionService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendQuestion(int sessionId, Question question){
                    Arrays.stream(roots).
                    map(root->WS_MESSAGE_TRANSFER_DESTINATION.formatted(root,sessionId))
                    .forEach(path->simpMessagingTemplate.convertAndSend(path,question));
    }
}
