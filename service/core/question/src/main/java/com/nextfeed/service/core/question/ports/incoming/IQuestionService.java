package com.nextfeed.service.core.question.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.question.QuestionValue;

public interface IQuestionService {

    void sendQuestion(int sessionId, QuestionValue question);
}
