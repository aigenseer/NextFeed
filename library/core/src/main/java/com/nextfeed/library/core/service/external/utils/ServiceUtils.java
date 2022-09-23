package com.nextfeed.library.core.service.external.utils;


import com.nextfeed.library.core.entity.session.Session;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.SessionManagerService;
import com.nextfeed.library.core.service.manager.SurveyTemplateManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ServiceUtils {

    private final SessionManagerService sessionManagerService;
    private final ParticipantManagerService participantManagerService;
    private final SurveyTemplateManagerService surveyTemplateManagerService;

    public void checkSessionId(Integer sessionId, boolean closedAllowed){
        if(sessionId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SessionId are not exists");
        Session session = sessionManagerService.getSessionById(sessionId);
        if (session == null || closedAllowed && session.getClosed() != 0L)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }

    public void checkSessionId(Integer sessionId){
        this.checkSessionId(sessionId, false);
    }


    public void checkParticipantId(Integer participantId){
        if (participantId == null || !participantManagerService.existsParticipantId(participantId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }

    public void checkTemplateId(int templateId){
        if (surveyTemplateManagerService.getTemplate(templateId)==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Template-Id %d are not exists", templateId));
    }

}
