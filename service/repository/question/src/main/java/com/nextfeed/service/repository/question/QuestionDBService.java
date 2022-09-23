package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.manager.repository.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class QuestionDBService extends AbstractService<QuestionEntity, QuestionRepository> {
    public QuestionDBService(QuestionRepository questionRepository) {
        super(questionRepository);
    }
}
