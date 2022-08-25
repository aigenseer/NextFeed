package com.nextfeed.service.socket.session;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.service.socket.SessionSocketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/session-socket")
public class SessionSocketRestController implements SessionSocketService {

    private final SessionDataService sessionDataService;

    @RequestMapping(value = "/v1/session/{sessionId}/notify/participant", method = RequestMethod.POST)
    public void sendNewParticipantToAll(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant){
        sessionDataService.sendNewParticipantToAll(sessionId, participant);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/notify/mood", method = RequestMethod.POST)
    public void sendMood(@PathVariable("sessionId") Integer sessionId, @RequestBody Double value){
        sessionDataService.sendMood(sessionId, value);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/notify/session/close", method = RequestMethod.GET)
    public void sendClose(@PathVariable("sessionId") Integer sessionId){
        sessionDataService.sendClose(sessionId);
    }

    @RequestMapping(value = "/v1/session/{sessionId}/notify/participants/status", method = RequestMethod.POST)
    public void sendConnectionStatus(@PathVariable("sessionId") Integer sessionId, @RequestBody List<Participant> participants){
        sessionDataService.sendConnectionStatus(sessionId, participants);
    }

}




