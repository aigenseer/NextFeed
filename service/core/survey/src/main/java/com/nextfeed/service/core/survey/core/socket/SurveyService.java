package com.nextfeed.service.core.survey.core.socket;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
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

    public void onCreateByPresenter(int sessionId, DTOEntities.SurveyDTO surveyDTO){
        var payload = DTO2EntityUtils.dto2Survey(surveyDTO);
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("presenter", sessionId);
        simpMessagingTemplate.convertAndSend(path, payload);
    }

    public void onCreateByParticipant(int sessionId, int surveyId, DTOEntities.SurveyTemplateDTO template){
        var payload = DTO2EntityUtils.dto2SurveyTemplate(template);
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("participant", sessionId);
        simpMessagingTemplate.convertAndSend(path, new SurveyParticipantDataResponse(surveyId, payload));
    }

    public void onClose(int sessionId, int surveyId){
        String path = WS_MESSAGE_CLOSE_TRANSFER_DESTINATION.formatted(sessionId,surveyId);

        simpMessagingTemplate.convertAndSend(path,sessionId);
    }

    public void onUpdate(int sessionId, DTOEntities.SurveyDTO surveyDTO){
        var payload = DTO2EntityUtils.dto2Survey(surveyDTO);
        String path = WS_MESSAGE_UPDATE_TRANSFER_DESTINATION.formatted(sessionId, surveyDTO.getId());
        simpMessagingTemplate.convertAndSend(path, payload);
    }

    public void onResult(int sessionId, DTOEntities.SurveyDTO surveyDTO){
        String presenterPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("presenter",sessionId, surveyDTO.getId());

        var payload = DTO2EntityUtils.dto2Survey(surveyDTO);
        simpMessagingTemplate.convertAndSend(presenterPath, payload);
        if(surveyDTO.getTemplate().getPublishResults()){
            String participantPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("participant",sessionId, surveyDTO.getId());
            simpMessagingTemplate.convertAndSend(participantPath, payload);
        }
    }
}
