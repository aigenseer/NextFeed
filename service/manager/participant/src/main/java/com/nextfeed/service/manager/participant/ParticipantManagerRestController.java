package com.nextfeed.service.manager.participant;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
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
@RequestMapping(value = "/api/participant-manager")
public class ParticipantManagerRestController implements ParticipantManagerService {

    public static void main(String[] args) {
        SpringApplication.run(ParticipantManagerRestController.class, args);
    }

    private final ParticipantManager participantManager;

    @RequestMapping(value = "/v1/session/{sessionId}", method = RequestMethod.POST)
    public Participant createParticipantBySessionId(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant) {
        return participantManager.createParticipantBySessionId(sessionId, participant.getNickname());
    }

    @RequestMapping(value = "/v1/session/{sessionId}/participants", method = RequestMethod.GET)
    public List<Participant> getParticipantsBySessionId(@PathVariable("sessionId") Integer sessionId) {
        return participantManager.getParticipantsBySessionId(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/participants/connected", method = RequestMethod.GET)
    public List<Participant> getConnectedParticipantsBySessionId(@PathVariable("sessionId") Integer sessionId) {
        return participantManager.getConnectedParticipantsBySessionId(sessionId);
    }

    @RequestMapping(value = "/v1/participant/{participantId}/session", method = RequestMethod.GET)
    public Session getSessionByParticipantId(@PathVariable("participantId") Integer participantId) {
        return participantManager.getSessionByParticipantId(participantId);
    }

    @RequestMapping(value = "/v1/participant/{participantId}/session/id", method = RequestMethod.GET)
    public Integer getSessionIdByParticipantId(@PathVariable("participantId") Integer participantId) {
        return participantManager.getSessionIdByParticipantId(participantId);
    }

    @RequestMapping(value = "/v1/participant/{participantId}/session/id", method = RequestMethod.PUT)
    public void updateConnectionStatusByParticipantId(@PathVariable("participantId") Integer participantId, @RequestBody Boolean status) {
        participantManager.updateConnectionStatusByParticipantId(participantId, status);
    }

    @RequestMapping(value = "/v1/participant/{participantId}/exists", method = RequestMethod.GET)
    public Boolean existsParticipantId(@PathVariable("participantId") Integer participantId) {
        return participantManager.existsParticipantId(participantId);
    }

    @RequestMapping(value = "/v1/participant/{participantId}", method = RequestMethod.GET)
    public Participant getParticipant(@PathVariable("participantId") Integer participantId) {
        return participantManager.getParticipantById(participantId);
    }

}
