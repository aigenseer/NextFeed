package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "participant-manager-service")
@LoadBalancerClient(name = "participant-manager-service", configuration = LoadBalancerConfiguration.class)
public interface ParticipantManagerService {

    @PostMapping("/participant-manager/v1/session/{sessionId}")
    public Participant createParticipantBySessionId(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant);



}
