package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.AddAnswerToSurveyRequest;
import com.nextfeed.library.core.proto.manager.CreateSurveyRequest;
import com.nextfeed.library.core.proto.manager.SurveyManagerServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import com.nextfeed.library.core.valueobject.surveytemplate.SurveyTemplateValue;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SurveyManagerServiceClient {

    @GrpcClient("survey-service")
    private SurveyManagerServiceGrpc.SurveyManagerServiceBlockingStub rpcService;

    public SurveyTemplateValue createSurvey(Integer sessionId, DTOEntities.SurveyTemplateDTO template) {
        var dto = rpcService.createSurvey(CreateSurveyRequest.newBuilder().setSessionId(sessionId).setTemplate(template).build());
        return SurveyTemplateValue.DTOBuilder().dto(dto).build();
    }

    public SurveyValueList getSurveysBySessionId(Integer sessionId) {
        return SurveyValueList.DTOBuilder().dto(rpcService.getSurveysBySessionId(DTORequestUtils.createIDRequest(sessionId))).build();
    }

    public void addAnswerToSurvey(int sessionId, int surveyId, int participantId, String answer) {
        rpcService.addAnswerToSurvey(AddAnswerToSurveyRequest.newBuilder()
                        .setSessionId(sessionId)
                        .setSurveyId(surveyId)
                        .setParticipantId(participantId)
                        .setAnswer(answer)
                .build());
    }

}
