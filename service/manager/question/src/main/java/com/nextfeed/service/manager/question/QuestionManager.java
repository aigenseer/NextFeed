package com.nextfeed.service.manager.question;

import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.repository.QuestionRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewQuestionRequest;
import com.nextfeed.library.core.service.socket.QuestionSocketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final QuestionRepositoryServiceClient questionRepositoryServiceClient;
    private final QuestionSocketServices questionSocketServices;
    private final ParticipantManagerServiceClient participantManagerServiceClient;


    public Boolean existsQuestionId(int questionId){
        return questionRepositoryServiceClient.findById(questionId).isPresent();
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
        var dto = questionRepositoryServiceClient.save(question);
        questionSocketServices.sendQuestion(sessionId, dto);
        return dto;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        var questionDTO = questionRepositoryServiceClient.findById(questionId);
        var participantDTO = participantManagerServiceClient.getParticipant(voterId);

        if (questionDTO.isPresent() &&
            participantDTO.isPresent() &&
            !questionDTO.get().getVoterEntityList().stream().map(DTOEntities.VoterEntityDTO::getParticipantId).toList().contains(participantDTO.get().getId())){
            questionRepositoryServiceClient.addVote(questionId, participantDTO.get().getId(), (rating? +1: -1));
            questionSocketServices.sendQuestion(sessionId, questionRepositoryServiceClient.findById(questionId).get());
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        var dto = questionRepositoryServiceClient.findById(questionId);
        if(dto.isPresent() && dto.get().getClosed() != 0){
            var r = questionRepositoryServiceClient.close(questionId);
            if(r.isPresent())
                questionSocketServices.sendQuestion(sessionId, r.get());
        }
    }

}
