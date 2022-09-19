package com.nextfeed.library.core.service.socket;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "session-socket-service")
public interface MoodSocketService {

    @RequestMapping(value = "/api/internal/mood-socket/v1/session/{sessionId}/notify/mood", method = RequestMethod.POST)
    public void sendMood(@PathVariable("sessionId") Integer sessionId, @RequestBody Double value);
}
