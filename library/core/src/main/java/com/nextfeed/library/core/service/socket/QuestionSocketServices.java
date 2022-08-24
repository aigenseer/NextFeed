package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionSocketServices {

    private final ServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "question-socket-service";

    public void sendQuestion(Integer sessionId, Question question){
        String path = "/question-socket/v1/socket/session/%d/question".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, path), question, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}