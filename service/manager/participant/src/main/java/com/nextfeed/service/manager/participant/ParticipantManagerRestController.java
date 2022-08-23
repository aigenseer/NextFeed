package com.nextfeed.service.manager.participant;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.service.ParticipantManagerService;
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


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity")
@EnableJpaRepositories("com.nextfeed.library.manager.repository")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping(value = "/participant-manager")
public class ParticipantManagerRestController implements ParticipantManagerService {

    public static void main(String[] args) {
        SpringApplication.run(ParticipantManagerRestController.class, args);
    }

    private final ParticipantManager participantManager;

    @PostMapping("/participant-manager/v1/session/{sessionId}")
    public Participant createParticipantBySessionId(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant) {
        return participantManager.createParticipantBySessionId(sessionId, participant.getNickname());
    }
}
