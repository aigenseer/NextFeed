package com.nextfeed.service.core.session.core;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class SessionDataService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendNewParticipantToAll(int sessionId, DTOEntities.ParticipantDTO participant){
        String presenterPath = String.format(SessionDataServicePresenterPath.sendNewParticipantToAll.toString(),sessionId);
        String participantPath = String.format(SessionDataServiceParticipantPath.sendNewParticipantToAll.toString(),sessionId);

        var payload = DTO2EntityUtils.dto2Participant(participant);
        simpMessagingTemplate.convertAndSend(presenterPath,  payload);
        simpMessagingTemplate.convertAndSend(participantPath, payload);
    }

    public void sendClose(int sessionId){
        String path = String.format(SessionDataServiceSharedPath.sendClosedToAll.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, "");
    }

    public void sendConnectionStatus(int sessionId, List<DTOEntities.ParticipantDTO> participants){
        String path = String.format(SessionDataServicePresenterPath.sendParticipantConnectionStatus.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, participants);
    }



}
