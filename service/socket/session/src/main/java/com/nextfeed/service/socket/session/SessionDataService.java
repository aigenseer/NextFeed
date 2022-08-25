package com.nextfeed.service.socket.session;

import com.nextfeed.library.core.entity.Participant;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

enum SessionDataServiceAdminPath {
    sendNewParticipantToAll("/socket/session-socket/v1/admin/session/%d/user/onjoin"),
    sendMood("/socket/session-socket/v1/admin/session/%d/mood/onupdate"),
    sendParticipantConnectionStatus("/socket/session-socket/v1/admin/session/%d/participant/connections/status");

    private final String path;
    SessionDataServiceAdminPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

enum SessionDataServiceParticipantPath {
    sendNewParticipantToAll("/socket/session-socket/v1/participant/session/%d/user/onjoin"),
    sendMood("/socket/session-socket/v1/participant/session/%d/mood/onupdate");

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

@Service
public class SessionDataService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    SessionDataService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendNewParticipantToAll(int sessionId, Participant participant){
        String adminPath = String.format(SessionDataServiceAdminPath.sendNewParticipantToAll.toString(),sessionId);
        String participantPath = String.format(SessionDataServiceParticipantPath.sendNewParticipantToAll.toString(),sessionId);

        simpMessagingTemplate.convertAndSend(adminPath, participant);
        simpMessagingTemplate.convertAndSend(participantPath,participant);
    }

    public void sendMood(int sessionId, double value){
        String adminPath = String.format(SessionDataServiceAdminPath.sendMood.toString(),sessionId);
        String participantPath = String.format(SessionDataServiceParticipantPath.sendMood.toString(),sessionId);

        simpMessagingTemplate.convertAndSend(adminPath, value);
        simpMessagingTemplate.convertAndSend(participantPath, value);
    }

    public void sendClose(int sessionId){
        String path = String.format(SessionDataServiceSharedPath.sendClosedToAll.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, "");
    }

    public void sendConnectionStatus(int sessionId, List<Participant> participants){
        String path = String.format(SessionDataServiceAdminPath.sendParticipantConnectionStatus.toString(),sessionId);
        simpMessagingTemplate.convertAndSend(path, participants);
    }



}

