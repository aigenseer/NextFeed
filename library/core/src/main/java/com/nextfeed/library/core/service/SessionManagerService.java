package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "session-manager-service")
@LoadBalancerClient(name = "session-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SessionManagerService {

    @PostMapping("/session-manager/v1/session/create")
    public Session createSession(@RequestBody NewSessionRequest request);

    @GetMapping("/session-manager/v1/session/{sessionId}")
    public Session getSessionById(@PathVariable("sessionId") Integer sessionId);

    @PostMapping("/session-manager/v1/session/save")
    public Session saveSession(@RequestBody Session session);

}
