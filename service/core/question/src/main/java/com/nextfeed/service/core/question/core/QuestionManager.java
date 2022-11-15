package com.nextfeed.service.core.question.core;

import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewQuestionRequest;
import com.nextfeed.library.core.service.socket.QuestionSocketServices;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import com.nextfeed.service.core.question.ports.incoming.IQuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionManager implements IQuestionManager {

    private final IQuestionRepositoryService questionRepositoryService;
    private final QuestionSocketServices questionSocketServices;
    private final ParticipantManagerServiceClient participantManagerServiceClient;


    public Boolean existsQuestionId(int questionId){
        return questionRepositoryService.findById(questionId).isInitialized();
    }

    public DTOEntities.QuestionDTO createQuestion(int sessionId, NewQuestionRequest request){
        var participant = participantManagerServiceClient.getParticipant(request.getParticipantId());
        DTOEntities.QuestionDTO question = DTOEntities.QuestionDTO.newBuilder()
                .setParticipant(participant.get())
                .setSessionId(sessionId)
                .setMessage(request.getMessage())
                .setCreated(request.getCreated())
                .setAnonymous(request.getAnonymous())
                .build();
        var dto = questionRepositoryService.save(question);
        questionSocketServices.sendQuestion(sessionId, dto);
        return dto;
    }

    public DTOEntities.QuestionDTO createQuestion(Integer sessionId, Integer participantId, String message, long created, boolean anonymous) {
        var request = NewQuestionRequest.newBuilder()
                .setParticipantId(participantId)
                .setMessage(message)
                .setCreated(created)
                .setAnonymous(anonymous)
                .build();
        return createQuestion(sessionId, request);
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        var questionDTO = questionRepositoryService.findById(questionId);
        var participantDTO = participantManagerServiceClient.getParticipant(voterId);

        if (questionDTO.isInitialized() &&
            participantDTO.isPresent() &&
            !questionDTO.getQuestion().getVoterEntityList().stream().map(DTOEntities.VoterEntityDTO::getParticipantId).toList().contains(participantDTO.get().getId())){
            questionRepositoryService.addVote(questionId, participantDTO.get().getId(), (rating? +1: -1));
            questionSocketServices.sendQuestion(sessionId, questionRepositoryService.findById(questionId).getQuestion());
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        var dto = questionRepositoryService.findById(questionId);
        if(dto.isInitialized() && dto.getQuestion().getClosed() != 0){
            var r = questionRepositoryService.close(questionId);
            if(r.isInitialized())
                questionSocketServices.sendQuestion(sessionId, r.getQuestion());
        }
    }

}
