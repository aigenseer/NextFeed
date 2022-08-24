package com.nextfeed.service.manager.session;


import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.dto.manager.session.NewSessionRequest;
import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/session-manager")
public class SessionManagerRestController implements SessionManagerService {

    public static void main(String[] args) {
        SpringApplication.run(SessionManagerRestController.class, args);
    }

    private final SessionManager sessionManager;


    @RequestMapping(value = "/v1/session", method = RequestMethod.POST)
    public Session createSession(@RequestBody NewSessionRequest request){
        return sessionManager.createSession(request.getName());
    }

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable("sessionId") Integer sessionId){
        sessionManager.deleteSession(sessionId);
    }

    @RequestMapping(value = "/v1/session", method = RequestMethod.PATCH)
    public Session saveSession(@RequestBody Session session){
        return sessionManager.saveSession(session);
    }

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.GET)
    public Session getSessionById(@PathVariable("sessionId") Integer sessionId){
        return sessionManager.getSessionById(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/check/closed", method = RequestMethod.GET)
    public Boolean isSessionClosed(@PathVariable("sessionId") Integer sessionId) {
        return sessionManager.isSessionClosed(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/check/exists", method = RequestMethod.GET)
    public Boolean existsSessionId(@PathVariable("sessionId") Integer sessionId) {
        return sessionManager.existsSessionId(sessionId);
    }

    @RequestMapping(value = "/v1/session/all", method = RequestMethod.GET)
    public List<Session> getAllSessions(){
        return sessionManager.getAllSessions();
    }

    @RequestMapping(value = "/v1/session/all/open", method = RequestMethod.GET)
    public List<Session> getAllOpenSessions(){
        return sessionManager.getAllOpenSessions();
    }

    @RequestMapping(value = "/v1/session/all/closed", method = RequestMethod.GET)
    public List<Session> getAllClosedSessions(){
        return sessionManager.getAllClosedSessions();
    }

    @RequestMapping(value = "/v1/session/close/all", method = RequestMethod.GET)
    public void closeAllOpenSessions(){
        sessionManager.closeAllOpenSessions();
    }

}
