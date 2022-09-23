package com.nextfeed.service.repository.participant;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.service.repository.ParticipantRepositoryService;
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
@RequestMapping(value = "/api/participant-repository")
public class ParticipantRepositoryRestController implements ParticipantRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(ParticipantRepositoryRestController.class, args);
    }

    private final ParticipantDBService participantDBService;

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Participant save(@RequestBody Participant participant) {
        return participantDBService.save(participant);
    }

    @RequestMapping(value = "/v1/get/id/{participantId}", method = RequestMethod.GET)
    public Participant findById(@PathVariable("participantId") Integer participantId) {
        return participantDBService.findById(participantId);
    }

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<Participant> get(@PathVariable("sessionId") Integer sessionId) {
        return List.of();
    }

}
