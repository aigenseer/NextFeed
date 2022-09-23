package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.entity.session.SessionEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "session-repository-service-x", url = "${nextfeed.service.session-repository-service.domain}:${nextfeed.service.session-repository-service.port}", path = "/api/session-repository")
public interface SessionRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Session save(@RequestBody SessionEntity session);

    @RequestMapping(value = "/v1/delete/id/{sessionId}", method = RequestMethod.GET)
    public void deleteById(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/get/all", method = RequestMethod.GET)
    public List<Session> findAll();

    @RequestMapping(value = "/v1/get/all/open", method = RequestMethod.GET)
    public List<Session> findAllOpen();

    @RequestMapping(value = "/v1/get/all/closed", method = RequestMethod.GET)
    public List<Session> findAllClosed();

    @RequestMapping(value = "/v1/find/id/{sessionId}", method = RequestMethod.GET)
    public SessionEntity findById(@PathVariable("sessionId") Integer sessionId);

    @RequestMapping(value = "/v1/get/id/{sessionId}", method = RequestMethod.GET)
    public Session getById(@PathVariable("sessionId") Integer sessionId);

}
