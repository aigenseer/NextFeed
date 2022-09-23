package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.repository.QuestionRepositoryService;
import com.nextfeed.library.core.service.repository.SessionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/session-repository")
public class SessionRepositoryRestController implements SessionRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(SessionRepositoryRestController.class, args);
    }

    private final SessionDBService sessionDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Session save(@RequestBody Session session) {
        return sessionDBService.save(session);
    }

    @RequestMapping(value = "/v1/get/id/{sessionId}", method = RequestMethod.GET)
    public void deleteById(@PathVariable("sessionId") Integer sessionId) {
        sessionDBService.deleteById(sessionId);
    }

    @RequestMapping(value = "/v1/get/all", method = RequestMethod.GET)
    public List<Session> findAll() {
        return sessionDBService.findAll();
    }

    @RequestMapping(value = "/v1/get/all/open", method = RequestMethod.GET)
    public List<Session> findAllOpen() {
        return sessionDBService.findAllOpen();
    }

    @RequestMapping(value = "/v1/get/all/closed", method = RequestMethod.GET)
    public List<Session> getAllOpenSessions() {
        return sessionDBService.findAllClosed();
    }

    @RequestMapping(value = "/v1/get/id/{sessionId}", method = RequestMethod.GET)
    public Session findById(@PathVariable("sessionId") Integer sessionId) {
        return sessionDBService.findById(sessionId);
    }

}
