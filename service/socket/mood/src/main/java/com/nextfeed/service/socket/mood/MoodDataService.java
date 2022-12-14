package com.nextfeed.service.socket.mood;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

enum MoodDataServicePresenterPath {
    sendMood("/socket/mood-socket/v1/presenter/session/%d/mood/onupdate");

    private final String path;
    MoodDataServicePresenterPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

enum MoodDataServiceParticipantPath {
    sendMood("/socket/mood-socket/v1/participant/session/%d/mood/onupdate");

    private final String path;
    MoodDataServiceParticipantPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}



@RequiredArgsConstructor
@Service
public class MoodDataService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMood(int sessionId, double value) {
        String presenterPath = String.format(MoodDataServicePresenterPath.sendMood.toString(), sessionId);
        String participantPath = String.format(MoodDataServiceParticipantPath.sendMood.toString(), sessionId);

        simpMessagingTemplate.convertAndSend(presenterPath, value);
        simpMessagingTemplate.convertAndSend(participantPath, value);
    }

}

