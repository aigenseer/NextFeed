package com.nextfeed.service.core.question.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;

public interface IQuestionService {

    void sendQuestion(int sessionId, DTOEntities.QuestionDTO question);
}
