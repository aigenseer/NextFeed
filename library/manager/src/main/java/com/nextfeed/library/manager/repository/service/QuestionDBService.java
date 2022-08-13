package com.nextfeed.library.manager.repository.service;

import com.nextfeed.library.core.entity.Question;
import com.nextfeed.library.manager.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionDBService extends AbstractService<Question, QuestionRepository> {
    public QuestionDBService(QuestionRepository questionRepository) {
        super(questionRepository);
    }
}
