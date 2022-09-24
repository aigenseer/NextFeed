package com.nextfeed.service.manager.question;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.QuestionDTO;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.dto.question.NewQuestionRequest;
import com.nextfeed.library.core.service.repository.QuestionRepositoryService;
import com.nextfeed.library.core.service.socket.QuestionSocketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final QuestionRepositoryService questionRepositoryService;
    private final QuestionSocketServices questionSocketServices;
    private final ParticipantManagerService participantManagerService;


    public Boolean existsQuestionId(int questionId){
        return questionRepositoryService.findById(questionId) != null;
    }

    public QuestionDTO createQuestion(int sessionId, NewQuestionRequest request){
        Participant participant = participantManagerService.getParticipant(request.getParticipantId());
        QuestionEntity question = QuestionEntity.builder()
                .participant_id(participant.getSession_id())
                .session_id(sessionId)
                .message(request.getMessage())
                .created(request.getCreated())
                .anonymous(request.getAnonymous())
                .build();
        var dto = questionRepositoryService.save(question);
        questionSocketServices.sendQuestion(sessionId, dto);
        return dto;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        QuestionDTO questionDTO = questionRepositoryService.findDTOById(questionId);
        Participant voter = participantManagerService.getParticipant(voterId);

        if (!questionDTO.getVoters().stream().map(VoterEntity::getParticipant_id).toList().contains(voter.getId())){
            questionRepositoryService.addVote(questionId, voter.getId(), (rating? +1: -1));
            questionSocketServices.sendQuestion(sessionId, questionRepositoryService.findDTOById(questionId));
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        QuestionEntity question = questionRepositoryService.findById(questionId);
        if(question.getClosed() == null){
            question.setClosed(System.currentTimeMillis());
            var dto = questionRepositoryService.save(question);
            questionSocketServices.sendQuestion(sessionId, dto);
        }
    }

}
