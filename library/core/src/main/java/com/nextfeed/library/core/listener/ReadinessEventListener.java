package com.nextfeed.library.core.listener;

import com.nextfeed.library.core.service.external.SharedCoreServices;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadinessEventListener {

    private final SharedCoreServices sharedCoreServices;

    @EventListener
    public void onEvent(AvailabilityChangeEvent<ReadinessState> event) {
        sharedCoreServices.notifyServiceStatusChance();
//        switch (event.getState()) {
//            case ACCEPTING_TRAFFIC:
//
//                break;
//            case REFUSING_TRAFFIC:
//                // we're back
//        }
    }

}
