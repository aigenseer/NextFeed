package com.nextfeed.library.core.service;

import com.nextfeed.library.core.entity.Participant;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "session-socket-service")
@LoadBalancerClient(name = "session-socket-service")
public interface SessionSocketService {

    @PostMapping("/session-socket/v1/session/socket/session/{sessionId}/notify/participant")
    public void sendNewParticipantToAll(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant);

    @PostMapping("/session-socket/v1/session/socket/session/{sessionId}/notify/mood")
    public void sendMood(@PathVariable("sessionId") Integer sessionId, @RequestBody Double value);

    @GetMapping("/session-socket/v1/session/socket/session/{sessionId}/notify/session/close")
    public void sendMood(@PathVariable("sessionId") Integer sessionId);

    @PostMapping("/session-socket/v1/session/socket/session/{sessionId}/notify/participants/status")
    public void sendNewParticipantToAll(@PathVariable("sessionId") Integer sessionId, @RequestBody List<Participant> participants);
}
