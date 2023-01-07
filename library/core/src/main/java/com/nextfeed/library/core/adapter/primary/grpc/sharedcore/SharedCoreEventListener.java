package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.*;
import com.nextfeed.library.core.utils.MicroserviceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedCoreEventListener {

    private final SharedCoreCacheService sharedCoreCacheService;
    private final MicroserviceUtils microserviceUtils;

    @EventListener
    public void handleSessionCreated(SessionCreatedEvent event) {
        var cache = SessionCache.builder().sessionId(event.getSessionId()).build();
        for (var participant :event.getParticipantValueList().getEntities()) {
            cache.addParticipant(participant);
        }
        sharedCoreCacheService.getSessionsCache().put(cache.getSessionId(), cache);
    }

    @EventListener
    public void handleSessionClosed(SessionClosedEvent event) {
        sharedCoreCacheService.getSessionsCache().remove(event.getSessionId());
    }

    @EventListener
    public void handleParticipantRegistered(ParticipantRegisteredEvent event) {
        if(sharedCoreCacheService.getSessionsCache().containsKey(event.getSessionId())){
            sharedCoreCacheService.getSessionsCache().get(event.getSessionId()).addParticipant(event.getParticipantValue().getEntity());
        }
    }

    @EventListener
    public void handleParticipantLoggedOff(ParticipantLoggedOffEvent event) {
        if(sharedCoreCacheService.getSessionsCache().containsKey(event.getSessionId())){
            sharedCoreCacheService.getSessionsCache().get(event.getSessionId()).removeParticipantById(event.getParticipantId());
        }
    }

    @EventListener
    public void handleServiceStatusChance(ServiceStatusChanceEvent event) {
        microserviceUtils.evictCache();
    }

}
