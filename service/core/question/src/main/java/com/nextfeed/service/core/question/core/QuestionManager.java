package com.nextfeed.service.core.question.core;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewQuestionRequest;
import com.nextfeed.library.core.service.socket.QuestionSocketServices;
import com.nextfeed.library.core.valueobject.question.QuestionValue;
import com.nextfeed.library.core.valueobject.question.QuestionValueList;
import com.nextfeed.service.core.question.core.db.QuestionRepositoryService;
import com.nextfeed.service.core.question.ports.incoming.IQuestionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionManager implements IQuestionManager {

    private final QuestionRepositoryService questionRepositoryService;
    private final QuestionSocketServices questionSocketServices;

    public Boolean existsQuestionId(int questionId){
        return questionRepositoryService.findById(questionId).isPresent();
    }

    public QuestionValue createQuestion(int sessionId, NewQuestionRequest request){
        QuestionEntity question = QuestionEntity.builder()
                .participant_id(request.getParticipantId())
                .session_id(sessionId)
                .message(request.getMessage())
                .created(request.getCreated())
                .anonymous(request.getAnonymous())
                .build();
        var dto = questionRepositoryService.save(question);
        questionSocketServices.sendQuestion(sessionId, dto);
        return dto;
    }

    public QuestionValue createQuestion(Integer sessionId, Integer participantId, String message, long created, boolean anonymous) {
        var request = NewQuestionRequest.newBuilder()
                .setParticipantId(participantId)
                .setMessage(message)
                .setCreated(created)
                .setAnonymous(anonymous)
                .build();
        return createQuestion(sessionId, request);
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        var optionalQuestionValue = questionRepositoryService.findById(questionId);

        if (optionalQuestionValue.isPresent() &&
            !optionalQuestionValue.get().getDTO().getVoterEntityList().stream().map(DTOEntities.VoterEntityDTO::getParticipantId).toList().contains(voterId)){
            questionRepositoryService.addVote(questionId, voterId, (rating? +1: -1));
            questionSocketServices.sendQuestion(sessionId, questionRepositoryService.findById(questionId).get());
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        var optionalQuestionValue = questionRepositoryService.findById(questionId);
        if(optionalQuestionValue.isPresent() && optionalQuestionValue.get().getEntity().getClosed() != 0){
            optionalQuestionValue = questionRepositoryService.close(questionId);
            if(optionalQuestionValue.isPresent())
                questionSocketServices.sendQuestion(sessionId, optionalQuestionValue.get());
        }
    }

    @Override
    public QuestionValueList findBySessionId(int sessionId) {
        return questionRepositoryService.findBySessionId(sessionId);
    }

}
