package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionSocketServices implements SessionSocketService {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "session-socket-service";

    @Value("#{new Integer('${nextfeed.service.session-socket-service.port}')}")
    private Integer port;

    public void sendNewParticipantToAll(Integer sessionId, Participant participant){
        String path = "/api/internal/session-socket/v1/session/%d/notify/participant".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), participant, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void sendClose(Integer sessionId){
        String path = "/api/internal/session-socket/v1/session/%d/notify/session/close".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.getRequest(serviceUtils.getURIByInstance(instance, port, path), String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void sendConnectionStatus(Integer sessionId, List<Participant> participants){
        String path = "/api/internal/session-socket/v1/session/%d/notify/participants/status".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                serviceUtils.postRequest(serviceUtils.getURIByInstance(instance, port, path), participants, String.class);
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
