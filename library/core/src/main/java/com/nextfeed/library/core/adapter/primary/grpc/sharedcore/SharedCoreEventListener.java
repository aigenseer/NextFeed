package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.ParticipantLoggedOffEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.ParticipantRegisteredEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.SessionClosedEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.SessionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedCoreEventListener {

    private final SharedCoreCacheService sharedCoreCacheService;

    @EventListener
    public void handleSessionCreated(SessionCreatedEvent request) {
        var cache = SessionCache.builder().sessionId(request.getSessionId()).build();
        for (var participant :request.getParticipantValueList().getEntities()) {
            cache.addParticipant(participant);
        }
        sharedCoreCacheService.getSessionCache().put(cache.getSessionId(), cache);
    }

    @EventListener
    public void handleSessionClosed(SessionClosedEvent request) {
        sharedCoreCacheService.getSessionCache().remove(request.getSessionId());
    }

    @EventListener
    public void handleParticipantRegistered(ParticipantRegisteredEvent request) {
        if(sharedCoreCacheService.getSessionCache().containsKey(request.getSessionId())){
            sharedCoreCacheService.getSessionCache().get(request.getSessionId()).addParticipant(request.getParticipantValue().getEntity());
        }
    }

    @EventListener
    public void handleParticipantLoggedOff(ParticipantLoggedOffEvent request) {
        if(sharedCoreCacheService.getSessionCache().containsKey(request.getSessionId())){
            sharedCoreCacheService.getSessionCache().get(request.getSessionId()).removeParticipantById(request.getParticipantId());
        }
    }

}
