package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionSocketServices {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "question-socket-service";

    @Value("#{new Integer('${nextfeed.service.question-socket-service.port}')}")
    private Integer port;

    public void sendQuestion(Integer sessionId, Question question){
        String path = "/api/internal/question-socket/v1/session/%d/question".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), question, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
