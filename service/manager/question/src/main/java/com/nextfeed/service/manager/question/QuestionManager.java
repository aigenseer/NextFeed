package com.nextfeed.service.manager.question;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
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

    public Question createQuestion(int sessionId, NewQuestionRequest request){
        Participant participant = participantManagerService.getParticipant(request.getParticipantId());
        Question question = Question.builder()
                .participant(participant)
                .session_id(sessionId)
                .message(request.getMessage())
                .rating(0)
                .created(request.getCreated())
                .anonymous(request.getAnonymous())
                .build();
        questionRepositoryService.save(question);
        questionSocketServices.sendQuestion(sessionId, question);
        return question;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        Question question = questionRepositoryService.findById(questionId);
        Participant voter = participantManagerService.getParticipant(voterId);

        if (!question.getVoters().contains(voter)){
            question.getVoters().add(voter);
            question.setRating(question.getRating() + (rating? +1: -1));
            questionRepositoryService.save(question);
            questionSocketServices.sendQuestion(sessionId, question);
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        Question question = questionRepositoryService.findById(questionId);
        if(question.getClosed() == null){
            question.setClosed(System.currentTimeMillis());
            questionRepositoryService.save(question);
            questionSocketServices.sendQuestion(sessionId, question);
        }
    }

}
