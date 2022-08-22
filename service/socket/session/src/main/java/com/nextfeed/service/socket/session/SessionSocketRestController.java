package com.nextfeed.service.socket.session;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.service.SessionSocketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/session-socket")
public class SessionSocketRestController implements SessionSocketService {

    private final SessionDataService sessionDataService;

    @PostMapping("/v1/session/socket/session/{sessionId}/notify/participant")
    public void sendNewParticipantToAll(@PathVariable("sessionId") Integer sessionId, @RequestBody Participant participant){
        sessionDataService.sendNewParticipantToAll(sessionId, participant);
    }

    @PostMapping("/v1/session/socket/session/{sessionId}/notify/mood")
    public void sendMood(@PathVariable("sessionId") Integer sessionId, @RequestBody Double value){
        sessionDataService.sendMood(sessionId, value);
    }

    @GetMapping("/v1/session/socket/session/{sessionId}/notify/session/close")
    public void sendClose(@PathVariable("sessionId") Integer sessionId){
        sessionDataService.sendClose(sessionId);
    }

    @PostMapping("/v1/session/socket/session/{sessionId}/notify/participants/status")
    public void sendConnectionStatus(@PathVariable("sessionId") Integer sessionId, @RequestBody List<Participant> participants){
        sessionDataService.sendConnectionStatus(sessionId, participants);
    }

}




