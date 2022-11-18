package com.nextfeed.service.core.question.ports.incoming;

import com.nextfeed.library.core.proto.manager.NewQuestionRequest;
import com.nextfeed.library.core.valueobject.question.QuestionValue;
import com.nextfeed.library.core.valueobject.question.QuestionValueList;

public interface IQuestionManager {

    Boolean existsQuestionId(int questionId);

    QuestionValue createQuestion(int sessionId, NewQuestionRequest request);

    QuestionValue createQuestion(Integer sessionId, Integer participantId, String message, long created, boolean anonymous);

    void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating);

    void closeQuestion(int sessionId, int questionId);

    QuestionValueList findBySessionId(int sessionId);

}
