package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "session-manager-service-x", url = "${nextfeed.service.session-manager-service.domain}:${nextfeed.service.session-manager-service.port}", path = "/api/session-manager")
public interface SessionManagerService {

    @RequestMapping(value = "/v1/session", method = RequestMethod.POST)
    public Session createSession(@RequestBody NewSessionRequest request);

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.GET)
    public Session getSessionById(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/session/{sessionId}/close", method = RequestMethod.GET)
    public void closeSession(@PathVariable("sessionId") Integer sessionId);

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
