package com.nextfeed.service.socket.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.service.socket.survey.dto.SurveyParticipantDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String WS_MESSAGE_CREATE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/%s/session/%d/survey/oncreate";
    private static final String WS_MESSAGE_CLOSE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/session/%d/survey/%d/onclose";
    private static final String WS_MESSAGE_RESULT_TRANSFER_DESTINATION = "/socket/survey-socket/v1/%s/session/%d/survey/%d/onresult";
    private static final String WS_MESSAGE_UPDATE_TRANSFER_DESTINATION = "/socket/survey-socket/v1/presenter/session/%d/survey/%d/onupdate";

    public void onCreateByPresenter(int sessionId, Survey survey){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("presenter", sessionId);
        simpMessagingTemplate.convertAndSend(path, survey);
    }

    public void onCreateByParticipant(int sessionId, int surveyId, SurveyTemplate template){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("participant", sessionId);
        simpMessagingTemplate.convertAndSend(path, new SurveyParticipantDataResponse(surveyId, template));
    }

    public void onClose(int sessionId, int surveyId){
        String path = WS_MESSAGE_CLOSE_TRANSFER_DESTINATION.formatted(sessionId,surveyId);

        simpMessagingTemplate.convertAndSend(path,sessionId);
    }

    public void onUpdate(int sessionId, Survey survey){
        String path = WS_MESSAGE_UPDATE_TRANSFER_DESTINATION.formatted(sessionId, survey.getId());
        simpMessagingTemplate.convertAndSend(path, survey);
    }

    public void onResult(int sessionId, Survey survey){
        String presenterPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("presenter",sessionId, survey.getId());

        simpMessagingTemplate.convertAndSend(presenterPath, survey);
        if(survey.getTemplate().isPublishResults()){
            String participantPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("participant",sessionId, survey.getId());
            simpMessagingTemplate.convertAndSend(participantPath, survey);
        }
    }
}
