package com.nextfeed.service.manager.question;

import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.core.entity.Session;
import com.nextfeed.library.core.service.ParticipantManagerService;
import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.dto.manager.question.NewQuestionRequest;
import com.nextfeed.library.manager.repository.service.ParticipantDBService;
import com.nextfeed.library.manager.repository.service.QuestionDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final QuestionDBService questionDBService;
    private final SessionManagerService sessionManagerService;
    //todo: muss noch gemacht werden
//    private final QuestionService questionService;
    private final ParticipantManagerService participantManagerService;


    public Boolean existsQuestionId(int questionId){
        return questionDBService.findById(questionId) != null;
    }

    public Question createQuestion(int sessionId, NewQuestionRequest request){
        Participant participant = participantManagerService.getParticipant(request.getParticipantId());
        Question question = Question.builder()
                .participant(participant)
                .message(request.getMessage())
                .rating(0)
                .created(request.getCreated())
                .anonymous(request.getAnonymous())
                .build();
        questionDBService.save(question);
        Session session = sessionManagerService.getSessionById(sessionId);
        session.getQuestions().add(question);
        sessionManagerService.saveSession(session);
//        questionService.sendQuestion(sessionId, question);
        return question;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        Question question = questionDBService.findById(questionId);
        Participant voter = participantManagerService.getParticipant(voterId);

        if (!question.getVoters().contains(voter)){
            question.getVoters().add(voter);
            question.setRating(question.getRating() + (rating? +1: -1));
            questionDBService.save(question);
            updateQuestion(sessionId, question);
//            questionService.sendQuestion(sessionId, question);
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        Question question = questionDBService.findById(questionId);
        if(question.getClosed() == null){
            question.setClosed(System.currentTimeMillis());
            questionDBService.save(question);
            updateQuestion(sessionId, question);
//            questionService.sendQuestion(sessionId, question);
        }
    }

    private void updateQuestion(int sessionId, Question question){
        sessionManagerService.getSessionById(sessionId).getQuestions().add(question);
    }


}
