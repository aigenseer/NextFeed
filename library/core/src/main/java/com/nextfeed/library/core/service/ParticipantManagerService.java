package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "participant-manager-service")
@LoadBalancerClient(name = "participant-manager-service", configuration = LoadBalancerConfiguration.class)
public interface ParticipantManagerService {

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.POST)
    public Participant createParticipantBySessionId(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant);

    @RequestMapping(value = "/v1/session/{sessionId}/participants", method = RequestMethod.GET)
    public List<Participant> getParticipantsBySessionId(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/participants/connected", method = RequestMethod.GET)
    public List<Participant> getConnectedParticipantsBySessionId(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/participant/{participantId}/session", method = RequestMethod.GET)
    public Session getSessionByParticipantId(@PathVariable("participantId") Integer participantId);

    @RequestMapping(value = "/v1/participant/{participantId}/session/id", method = RequestMethod.GET)
    public Integer getSessionIdByParticipantId(@PathVariable("participantId") Integer participantId);

    @RequestMapping(value = "/v1/participant/{participantId}/session/id", method = RequestMethod.PATCH)
    public void updateConnectionStatusByParticipantId(@PathVariable("participantId") Integer participantId, @RequestBody Boolean status);

    @RequestMapping(value = "/v1/participant/{participantId}/exists", method = RequestMethod.GET)
    public Boolean existsParticipantId(@PathVariable("participantId") Integer participantId);


}
