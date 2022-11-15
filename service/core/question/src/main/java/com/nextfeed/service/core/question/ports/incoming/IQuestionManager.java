package com.nextfeed.service.core.question.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.NewQuestionRequest;

public interface IQuestionManager {

    Boolean existsQuestionId(int questionId);

    DTOEntities.QuestionDTO createQuestion(int sessionId, NewQuestionRequest request);

    DTOEntities.QuestionDTO createQuestion(Integer sessionId, Integer participantId, String message, long created, boolean anonymous);

    void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating);

    void closeQuestion(int sessionId, int questionId);

}
