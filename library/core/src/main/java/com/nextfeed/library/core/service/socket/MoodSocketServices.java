package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.utils.SocketServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoodSocketServices implements MoodSocketService {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "mood-socket-service";

    @Value("#{new Integer('${nextfeed.service.mood-socket-service.port}')}")
    private Integer port;

    public void sendMood(Integer sessionId, Double value){
        String path = "/api/internal/mood-socket/v1/session/%d/notify/mood".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), value, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
