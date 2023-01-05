package com.nextfeed.library.core.service.external.utils;


import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.SharedCoreCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ServiceUtils {

    private final SharedCoreCacheService sharedCoreCacheService;

    public void checkSessionId(Integer sessionId, boolean closedAllowed){
        if(sessionId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SessionId are not exists");
        if (!sharedCoreCacheService.existsSessionId(sessionId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }

    public void checkSessionId(Integer sessionId){
        this.checkSessionId(sessionId, false);
    }

    public void checkParticipantId(Integer sessionId, Integer participantId){
        if (participantId == null || !sharedCoreCacheService.existsParticipantIdBySessionId(sessionId, participantId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }




}
