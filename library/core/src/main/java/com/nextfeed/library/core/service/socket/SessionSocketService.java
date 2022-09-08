package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.entity.Participant;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "session-socket-service")
@LoadBalancerClient(name = "session-socket-service")
public interface SessionSocketService {

    @RequestMapping(value = "/api/internal/session-socket/v1/session/{sessionId}/notify/participant", method = RequestMethod.POST)
    public void sendNewParticipantToAll(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant);

    @RequestMapping(value = "/api/internal/session-socket/v1/session/{sessionId}/notify/session/close", method = RequestMethod.GET)
    public void sendClose(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/api/internal/session-socket/v1/session/{sessionId}/notify/participants/status", method = RequestMethod.POST)
    public void sendConnectionStatus(@PathVariable("sessionId") Integer sessionId, @RequestBody List<Participant> participants);
}
