package com.nextfeed.service.core.survey.core.socket;

import com.nextfeed.library.core.valueobject.survey.SurveyValue;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import com.nextfeed.service.core.survey.core.socket.dto.SurveyParticipantDataResponse;
import com.nextfeed.service.core.survey.ports.incoming.ISurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SurveyService implements ISurveyService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String WS_MESSAGE_CREATE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/%s/session/%d/survey/oncreate";
    private static final String WS_MESSAGE_CLOSE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/session/%d/survey/%d/onclose";
    private static final String WS_MESSAGE_RESULT_TRANSFER_DESTINATION = "/socket/survey-socket/v1/%s/session/%d/survey/%d/onresult";
    private static final String WS_MESSAGE_UPDATE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/presenter/session/%d/survey/%d/onupdate";

    public void onCreateByPresenter(int sessionId, SurveyValue surveyValue){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("presenter", sessionId);
        simpMessagingTemplate.convertAndSend(path, surveyValue.getEntity());
    }

    public void onCreateByParticipant(int sessionId, int surveyId, SurveyTemplateValue surveyTemplateValue){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("participant", sessionId);
        simpMessagingTemplate.convertAndSend(path, new SurveyParticipantDataResponse(surveyId, surveyTemplateValue.getEntity()));
    }

    public void onClose(int sessionId, int surveyId){
        String path = WS_MESSAGE_CLOSE_TRANSFER_DESTINATION.formatted(sessionId,surveyId);

        simpMessagingTemplate.convertAndSend(path,sessionId);
    }

    public void onUpdate(int sessionId, SurveyValue surveyValue){
        String path = WS_MESSAGE_UPDATE_TRANSFER_DESTINATION.formatted(sessionId, surveyValue.getEntity().getId());
        simpMessagingTemplate.convertAndSend(path, surveyValue.getEntity());
    }

    public void onResult(int sessionId, SurveyValue surveyValue){
        String presenterPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("presenter",sessionId, surveyValue.getEntity().getId());

        simpMessagingTemplate.convertAndSend(presenterPath, surveyValue.getEntity());
        if(surveyValue.getEntity().getTemplate().isPublishResults()){
            String participantPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("participant",sessionId, surveyValue.getEntity().getId());
            simpMessagingTemplate.convertAndSend(participantPath, surveyValue.getEntity());
        }
    }
}
