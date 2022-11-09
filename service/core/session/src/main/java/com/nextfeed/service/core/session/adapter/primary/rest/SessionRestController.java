package com.nextfeed.service.core.session.adapter.primary.rest;

import com.nextfeed.library.core.entity.session.SessionMetadata;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.service.external.dto.authorization.NewSessionRequest;
import com.nextfeed.library.core.service.external.utils.ServiceUtils;
import com.nextfeed.service.core.session.core.CSVManager;
import com.nextfeed.service.core.session.core.SessionManager;
import com.nextfeed.service.core.session.ports.incoming.ISessionManager;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/session-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionRestController{

    private final ISessionManager sessionManager;
    private final ServiceUtils serviceUtils;
//    private final TokenService tokenService;
//    private final WebSocketHolderService webSocketHolderService;
    private final CSVManager csvManager;


    @PostMapping("/v1/session/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody NewSessionRequest request) {
        var session = sessionManager.createSession(request.getName());
        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id", session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/v1/session/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(!sessionManager.isSessionClosed(sessionId))
            sessionManager.closeSession(sessionId);
    }

    @GetMapping("/v1/session/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        //todo: muss noch angepasst werden
//        if(!SecurityContextHolderUtils.isCurrentUser()) tokenService.checkSessionIdByToken(token, sessionId);
        serviceUtils.checkSessionId(sessionId);

        if(sessionManager.isSessionClosed(sessionId)){
            return null;
        }

        var session = sessionManager.getSessionById(sessionId);
        List<DTOEntities.ParticipantDTO> participants = session.getSession().getParticipants().getParticipantsList();
        List<DTOEntities.QuestionDTO> questions = session.getSession().getQuestions().getQuestionsList();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/v1/session/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        serviceUtils.checkSessionId(sessionId);
        if(sessionManager.isSessionClosed(sessionId)) sessionManager.deleteSession(sessionId);
    }

    @GetMapping("/v1/session/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {
        return sessionManager.getAllClosedSessions().getSessionsList()
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
        return sessionManager.getSessionById(sessionId).getSession();
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
