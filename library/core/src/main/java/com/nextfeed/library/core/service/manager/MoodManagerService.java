package com.nextfeed.library.core.service.manager;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.core.service.manager.dto.mood.NewMoodRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "mood-manager-service-x", url = "${nextfeed.service.mood-manager-service.domain}:${nextfeed.service.mood-manager-service.port}", path = "/api/mood-manager")
public interface MoodManagerService {

    @RequestMapping(value = "/v1/session/{sessionId}/mood/create", method = RequestMethod.POST)
    public MoodEntity addMoodValueToSession(@PathVariable("sessionId") Integer sessionId, @RequestBody NewMoodRequest request);

    @RequestMapping(value = "/v1/session/{sessionId}/mood/calculated/create", method = RequestMethod.POST)
    public MoodEntity createCalculatedMoodValue(@PathVariable("sessionId") Integer sessionId, @RequestBody NewCalculatedMoodRequest request);

}
