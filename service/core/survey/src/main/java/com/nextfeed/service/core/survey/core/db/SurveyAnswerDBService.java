package com.nextfeed.service.core.survey.core.db;


import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.manager.repository.service.AbstractService;
import com.nextfeed.service.core.survey.ports.outgoing.SurveyAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyAnswerDBService extends AbstractService<SurveyAnswer, SurveyAnswerRepository> {

    public SurveyAnswerDBService(SurveyAnswerRepository surveyAnswerRepository) {
        super(surveyAnswerRepository);
    }

    public Optional<Integer> searchAnswerId(Integer participantId, Integer surveyId){
        var r = repo.searchAnswerId(participantId, surveyId);
        if(r.size() == 0) return Optional.empty();
        return Optional.of(r.get(0));
    }

    public boolean existsSurveyAnswerByParticipant(Integer participantId, Integer surveyId){
        return searchAnswerId(participantId, surveyId).isPresent();
    }

}
