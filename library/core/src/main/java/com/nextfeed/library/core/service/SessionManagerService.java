package com.nextfeed.library.core.service;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "session-manager-service")
@LoadBalancerClient(name = "session-manager-service", configuration = LoadBalancerConfiguration.class)
public interface SessionManagerService {


    @RequestMapping(value = "/v1/session", method = RequestMethod.POST)
    public Session createSession(@RequestBody NewSessionRequest request);

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session", method = RequestMethod.PATCH)
    public Session saveSession(@RequestBody Session session);

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.GET)
    public Session getSessionById(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/check/closed", method = RequestMethod.GET)
    public Boolean isSessionClosed(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/check/exists", method = RequestMethod.GET)
    public Boolean existsSessionId(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/all", method = RequestMethod.GET)
    public List<Session> getAllSessions();

    @RequestMapping(value = "/v1/session/all/open", method = RequestMethod.GET)
    public List<Session> getAllOpenSessions();

    @RequestMapping(value = "/v1/session/all/closed", method = RequestMethod.GET)
    public List<Session> getAllClosedSessions();

    @RequestMapping(value = "/v1/session/close/all", method = RequestMethod.GET)
    public void closeAllOpenSessions();


}
