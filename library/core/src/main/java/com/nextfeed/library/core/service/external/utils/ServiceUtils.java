package com.nextfeed.library.core.service.external.utils;


import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SurveyTemplateManagerServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ServiceUtils {

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private final ParticipantManagerServiceClient participantManagerServiceClient;
    private final SurveyTemplateManagerServiceClient surveyTemplateManagerServiceClient;

    public void checkSessionId(Integer sessionId, boolean closedAllowed){
        if(sessionId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SessionId are not exists");
        var session = sessionManagerServiceClient.getSessionById(sessionId);
        if (session.isEmpty() || closedAllowed && session.get().getClosed() != 0L)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }

    public void checkSessionId(Integer sessionId){
        this.checkSessionId(sessionId, false);
    }


    public void checkParticipantId(Integer participantId){
        if (participantId == null || !participantManagerServiceClient.existsParticipantId(participantId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }

    public void checkTemplateId(int templateId){
        if (surveyTemplateManagerServiceClient.getTemplateById(templateId).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Template-Id %d are not exists", templateId));
    }

}
