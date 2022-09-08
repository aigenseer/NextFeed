package com.nextfeed.service.socket.mood;

import com.nextfeed.library.core.service.socket.MoodSocketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/internal/mood-socket")
public class MoodSocketRestController implements MoodSocketService {

    private final MoodDataService sessionDataService;

    @RequestMapping(value = "/v1/session/{sessionId}/notify/mood", method = RequestMethod.POST)
    public void sendMood(@PathVariable("sessionId") Integer sessionId, @RequestBody Double value){
        sessionDataService.sendMood(sessionId, value);
    }

}




