package com.nextfeed.service.external.session;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.entity.SessionMetadata;
import com.nextfeed.library.core.service.external.SessionService;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.library.core.service.manager.QuestionManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/session-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionRestController implements SessionService {

    public static void main(String[] args) {
        SpringApplication.run(SessionRestController.class, args);
    }

    private final SessionManagerService sessionManagerService;
    private final QuestionManagerService questionManagerService;
    private final ServiceUtils serviceUtils;
//    private final TokenService tokenService;
//    private final WebSocketHolderService webSocketHolderService;
    private final CSVManager csvManager;


    @PostMapping("/v1/session/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody NewSessionRequest request) {
        Session session = sessionManagerService.createSession(request);
        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id", session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/v1/session/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(!sessionManagerService.isSessionClosed(sessionId))
            sessionManagerService.closeSession(sessionId);
    }

    @GetMapping("/v1/session/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        //todo: muss noch angepasst werden
//        if(!SecurityContextHolderUtils.isCurrentUser()) tokenService.checkSessionIdByToken(token, sessionId);
        serviceUtils.checkSessionId(sessionId);

        if(sessionManagerService.isSessionClosed(sessionId)){
            return null;
        }

        Session session = sessionManagerService.getSessionById(sessionId);
        Set<Participant> participants = session.getParticipants();
        Set<Question> questions = session.getQuestions();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/v1/session/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(sessionManagerService.isSessionClosed(sessionId)) sessionManagerService.deleteSession(sessionId);
    }

    @PostMapping("/v1/session/{sessionId}/question/create")
    public Question createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId){
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(request.getParticipantId());
        return questionManagerService.createQuestion(sessionId, request);
    }

    @GetMapping("/v1/session/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {
        return sessionManagerService.getAllClosedSessions()
                .stream()
                .map(this::toMetadata)
                .collect(Collectors.toList());
    }

    private SessionMetadata toMetadata(Session session){
        return new SessionMetadata(session.getId(),session.getName(),session.getClosed());
    }

    @GetMapping("/v1/session/presenter/{sessionId}/data")
    public Session getSessionData(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId, false);
        return sessionManagerService.getSessionById(sessionId);
    }

    @GetMapping("/v1/session/presenter/{sessionId}/participant/{participantId}/kill/{blocked}")
    public void killParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("participantId") Integer participantId, @PathVariable("blocked") Boolean blocked) {
        serviceUtils.checkSessionId(sessionId);
        serviceUtils.checkParticipantId(participantId);
        //todo: muss noch gemacht werden
//        if(blocked){
//            webSocketHolderService.blockRemoteAddrByParticipantId(participantId);
//        }
//        webSocketHolderService.killConnectionByParticipantId(participantId);
    }

    //TODO: LFD-92 //Aktuell Testdaten
    @GetMapping("/v1/session/presenter/{sessionId}/data/download")
    public FileSystemResource downloadSessionData(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        try {
            File tempFile = csvManager.buildSessionZip(sessionId);
            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Failed to create data by session id %d", sessionId));
        }
    }

}
