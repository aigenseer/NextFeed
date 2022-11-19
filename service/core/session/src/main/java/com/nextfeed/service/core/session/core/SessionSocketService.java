package com.nextfeed.service.core.session.core;

import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import com.nextfeed.service.core.session.ports.incoming.ISessionSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

enum SessionDataServicePresenterPath {
    sendNewParticipantToAll("/socket/session-socket/v1/presenter/session/%d/user/onjoin"),
    sendParticipantConnectionStatus("/socket/session-socket/v1/presenter/session/%d/participant/connections/status");

    private final String path;
    SessionDataServicePresenterPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

enum SessionDataServiceParticipantPath {
    sendNewParticipantToAll("/socket/session-socket/v1/participant/session/%d/user/onjoin");

    private final String path;
    SessionDataServiceParticipantPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

enum SessionDataServiceSharedPath {
    sendClosedToAll("/socket/session-socket/v1/session/%d/onclose");

    private final String path;
    SessionDataServiceSharedPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

@RequiredArgsConstructor
@Service
public class SessionSocketService implements ISessionSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendNewParticipantToAll(int sessionId, ParticipantValue participant){
        String presenterPath = String.format(SessionDataServicePresenterPath.sendNewParticipantToAll.toString(),sessionId);
        String participantPath = String.format(SessionDataServiceParticipantPath.sendNewParticipantToAll.toString(),sessionId);

        simpMessagingTemplate.convertAndSend(presenterPath,  participant.getEntity());
        simpMessagingTemplate.convertAndSend(participantPath, participant.getEntity());
    }

    public void sendClose(int sessionId){
        String path = String.format(SessionDataServiceSharedPath.sendClosedToAll.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, "");
    }

    public void sendConnectionStatus(int sessionId, ParticipantValueList participantValueList){
        String path = String.format(SessionDataServicePresenterPath.sendParticipantConnectionStatus.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, participantValueList.getEntities());
    }



}

