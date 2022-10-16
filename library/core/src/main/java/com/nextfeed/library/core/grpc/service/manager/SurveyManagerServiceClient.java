package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SurveyManagerServiceClient {

    @GrpcClient("survey-manager-service")
    private SurveyManagerServiceGrpc.SurveyManagerServiceBlockingStub rpcService;

    public DTOEntities.SurveyTemplateDTO createSurvey(Integer sessionId, DTOEntities.SurveyTemplateDTO template) {
        return rpcService.createSurvey(CreateSurveyRequest.newBuilder().setSessionId(sessionId).setTemplate(template).build());
    }

    public DTOEntities.SurveyDTOList getSurveysBySessionId(Integer sessionId) {
        return rpcService.getSurveysBySessionId(DTORequestUtils.createIDRequest(sessionId));
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
