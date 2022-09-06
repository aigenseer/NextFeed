package com.nextfeed.library.core.service.external;

import com.nextfeed.library.core.config.LoadBalancerConfiguration;
import com.nextfeed.library.core.entity.*;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.manager.dto.session.NewSessionRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@FeignClient(name = "session-service")
@LoadBalancerClient(name = "session-service", configuration = LoadBalancerConfiguration.class)
public interface SessionService {

    @PostMapping("/api/session-service/v1/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody NewSessionRequest request);

    @GetMapping("/api/session-service/v1/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId);

    @GetMapping("/api/session-service/v1/{sessionId}/initial")
    public Map<String,Object> getSessionInitialData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token);

    @DeleteMapping("/api/session-service/v1/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId);

    @PostMapping("/api/session-service/v1/{sessionId}/question/create")
    public Question createQuestion(@RequestBody NewQuestionRequest request, @PathVariable("sessionId") Integer sessionId);

    @GetMapping("/api/session-service/v1/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata();

    @GetMapping("/api/session-service/v1/presenter/{sessionId}/data")
    public Session getSessionData(@PathVariable("sessionId") Integer sessionId);

    @GetMapping("/api/session-service/v1/presenter/{sessionId}/participant/{participantId}/kill/{blocked}")
    public void killParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("participantId") Integer participantId, @PathVariable("blocked") Boolean blocked);


    @GetMapping("/api/session-service/v1/presenter/{sessionId}/data/download")
    public FileSystemResource downloadSessionData(@PathVariable("sessionId") Integer sessionId);


}
