package com.nextfeed.service.external.session;

import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.entity.session.SessionMetadata;
import com.nextfeed.library.core.grpc.service.manager.QuestionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.external.dto.authorization.NewQuestionRequest;
import com.nextfeed.library.core.service.external.dto.authorization.NewSessionRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
import java.util.stream.Collectors;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/session-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionRestController{

    public static void main(String[] args) {
        SpringApplication.run(SessionRestController.class, args);
    }

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final QuestionManagerServiceClient questionManagerServiceClient;
    private final ServiceUtils serviceUtils;
//    private final TokenService tokenService;
//    private final WebSocketHolderService webSocketHolderService;
    private final CSVManager csvManager;


    @PostMapping("/v1/session/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody NewSessionRequest request) {
        var session = sessionManagerServiceClient.createSession(request.getName());
        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id", session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/v1/session/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(!sessionManagerServiceClient.isSessionClosed(sessionId))
            sessionManagerServiceClient.closeSession(sessionId);
    }

    @GetMapping("/v1/session/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        //todo: muss noch angepasst werden
//        if(!SecurityContextHolderUtils.isCurrentUser()) tokenService.checkSessionIdByToken(token, sessionId);
        serviceUtils.checkSessionId(sessionId);

        if(sessionManagerServiceClient.isSessionClosed(sessionId)){
            return null;
        }

        var session = sessionManagerServiceClient.getSessionById(sessionId);
        List<DTOEntities.ParticipantDTO> participants = session.get().getParticipants().getParticipantsList();
        List<DTOEntities.QuestionDTO> questions = session.get().getQuestions().getQuestionsList();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/v1/session/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(sessionManagerServiceClient.isSessionClosed(sessionId)) sessionManagerServiceClient.deleteSession(sessionId);
    }

    @GetMapping("/v1/session/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {
        return sessionManagerServiceClient.getAllClosedSessions().getSessionsList()
                .stream()
                .map(this::toMetadata)
                .collect(Collectors.toList());
    }

    private SessionMetadata toMetadata(DTOEntities.SessionDTO session){
        return new SessionMetadata(session.getId(),session.getName(),session.getClosed());
    }

    @GetMapping("/v1/session/presenter/{sessionId}/data")
    public DTOEntities.SessionDTO getSessionData(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId, false);
        return sessionManagerServiceClient.getSessionById(sessionId).get();
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
