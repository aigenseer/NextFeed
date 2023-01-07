package com.nextfeed.library.security;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
@EnableScheduling
public class WebSocketMetric{

    private final MeterRegistry registry;
    private final SimpUserRegistry simpUserRegistry;
    private AtomicInteger activeConnection;

    @PostConstruct
    public void init(){
        activeConnection = registry.gauge("websocket-active-connection", new AtomicInteger(0));
    }

    @Scheduled(fixedRate = 3000)
    public void reportCurrentTime() {
        var count = this.simpUserRegistry
                .getUsers()
                .stream()
                .map(SimpUser::getName).toList().size();
        activeConnection.set(count);
    }

}