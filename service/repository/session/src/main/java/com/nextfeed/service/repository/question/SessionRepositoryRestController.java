package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity.session")
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
    private final ParticipantRepositoryService participantRepositoryService;
    private final QuestionRepositoryService questionRepositoryService;
    private final SurveyRepositoryService surveyRepositoryService;
    private final MoodRepositoryService moodRepositoryService;

    private Session mapEntityToDTO(SessionEntity s){
        return Session.builder()
                .id(s.getId())
                .participants(participantRepositoryService.findBySessionId(s.getId()))
                .questions(questionRepositoryService.findBySessionId(s.getId()))
                .surveys(surveyRepositoryService.findBySessionId(s.getId()))
                .moodEntities(moodRepositoryService.findBySessionId(s.getId()))
                .closed(s.getClosed())
                .sessionCode(s.getSessionCode())
                .name(s.getName())
                .build();
    }

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Session save(@RequestBody SessionEntity session) {
        return mapEntityToDTO(sessionDBService.save(session));
    }

    @RequestMapping(value = "/v1/delete/id/{sessionId}", method = RequestMethod.GET)
    public void deleteById(@PathVariable("sessionId") Integer sessionId) {
        sessionDBService.deleteById(sessionId);
    }

    @RequestMapping(value = "/v1/get/all", method = RequestMethod.GET)
    public List<Session> findAll() {
        return sessionDBService.findAll().stream().map(this::mapEntityToDTO).toList();
    }

    @RequestMapping(value = "/v1/get/all/open", method = RequestMethod.GET)
    public List<Session> findAllOpen() {
        return sessionDBService.findAllOpen().stream().map(this::mapEntityToDTO).toList();
    }

    @RequestMapping(value = "/v1/get/all/closed", method = RequestMethod.GET)
    public List<Session> findAllClosed() {
        return sessionDBService.findAllClosed().stream().map(this::mapEntityToDTO).toList();
    }

    @RequestMapping(value = "/v1/find/id/{sessionId}", method = RequestMethod.GET)
    public SessionEntity findById(@PathVariable("sessionId") Integer sessionId) {
        return sessionDBService.findById(sessionId);
    }

    @RequestMapping(value = "/v1/get/id/{sessionId}", method = RequestMethod.GET)
    public Session getById(@PathVariable("sessionId") Integer sessionId) {
        return mapEntityToDTO(sessionDBService.findById(sessionId));
    }

}
