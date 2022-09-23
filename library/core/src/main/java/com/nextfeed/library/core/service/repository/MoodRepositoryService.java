package com.nextfeed.library.core.service.repository;

import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.core.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "mood-repository-service-x", url = "${nextfeed.service.mood-repository-service.domain}:${nextfeed.service.mood-repository-service.port}", path = "/api/mood-repository")
public interface MoodRepositoryService {

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public MoodEntity save(@RequestBody MoodEntity moodEntity);

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<MoodEntity> get(@PathVariable("sessionId") Integer sessionId);

}