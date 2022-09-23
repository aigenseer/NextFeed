package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.entity.Participant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "participant-repository-service-x", url = "${nextfeed.service.participant-repository-service.domain}:${nextfeed.service.participant-repository-service.port}", path = "/api/participant-repository")
public interface ParticipantRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Participant save(@RequestBody Participant participant);

    @RequestMapping(value = "/v1/get/id/{participantId}", method = RequestMethod.GET)
    public Participant findById(@PathVariable("participantId") Integer participantId);

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<Participant> get(@PathVariable("sessionId") Integer sessionId);

}
