package com.nextfeed.service.socket.question;

import com.nextfeed.library.core.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/socket/question-socket/v1/%s/session/%d/question/onupdate";
    private static final String[] roots = {"admin","participant"};

    public void sendQuestion(int sessionId, Question question){
                    Arrays.stream(roots).
                    map(root->WS_MESSAGE_TRANSFER_DESTINATION.formatted(root,sessionId))
                    .forEach(path->simpMessagingTemplate.convertAndSend(path,question));
    }
}
