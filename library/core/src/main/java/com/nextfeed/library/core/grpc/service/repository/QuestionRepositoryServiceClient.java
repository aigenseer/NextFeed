package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.QuestionRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.repository.VoteQuestionRequest;
import com.nextfeed.library.core.utils.DTORequestUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.Optional;

public class QuestionRepositoryServiceClient {

    @GrpcClient("question-repository-service")
    private QuestionRepositoryServiceGrpc.QuestionRepositoryServiceBlockingStub rpcService;

    public DTOEntities.QuestionDTO save(DTOEntities.QuestionDTO dto) {
        return rpcService.save(dto);
    }

    public Optional<DTOEntities.QuestionDTO> findById(Integer id) {
        return Optional.of(rpcService.findById(DTORequestUtils.createIDRequest(id)).getQuestion());
    }

    public void addVote(Integer questionId, Integer participantId, Integer rating) {
        rpcService.addVote(VoteQuestionRequest.newBuilder().setQuestionId(questionId).setParticipantId(participantId).setRating(rating).build());
    }

    public DTOEntities.QuestionDTOList findBySessionId(Integer id) {
        return rpcService.findBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public void deleteAllBySessionId(Integer id) {
        rpcService.deleteAllBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public Optional<DTOEntities.QuestionDTO> close(Integer id) {
        return Optional.of(rpcService.close(DTORequestUtils.createIDRequest(id)).getQuestion());
    }

}
