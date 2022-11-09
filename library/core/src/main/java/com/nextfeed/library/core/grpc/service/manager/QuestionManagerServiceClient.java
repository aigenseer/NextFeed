package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.utils.DTORequestUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class QuestionManagerServiceClient {

    @GrpcClient("question-service")
    private QuestionManagerServiceGrpc.QuestionManagerServiceBlockingStub rpcService;

    public boolean existsQuestionId(Integer id) {
        return rpcService.existsQuestionId(DTORequestUtils.createIDRequest(id)).getResult();
    }

    public DTOEntities.QuestionDTO createQuestion(Integer sessionId, Integer participantId, String message, long created, boolean anonymous) {
        var request = NewQuestionRequest.newBuilder()
                .setParticipantId(participantId)
                .setMessage(message)
                .setCreated(created)
                .setAnonymous(anonymous)
                .build();
        return rpcService.createQuestion(CreateQuestionRequest.newBuilder().setSessionId(sessionId).setRequest(request).build());
    }

    public void ratingUpByQuestionId(Integer sessionId, Integer questionId, Integer participantId, Boolean rating) {
        rpcService.ratingUpByQuestionId(RatingUpByQuestionIdRequest.newBuilder()
                        .setSessionId(sessionId)
                        .setQuestionId(questionId)
                        .setRating(rating)
                        .setParticipantId(participantId)
                        .build());
    }

    public void closeQuestion(Integer sessionId, Integer questionId) {
        rpcService.closeQuestion(CloseQuestionRequest.newBuilder()
                .setSessionId(sessionId)
                .setQuestionId(questionId)
                .build());
    }

}
