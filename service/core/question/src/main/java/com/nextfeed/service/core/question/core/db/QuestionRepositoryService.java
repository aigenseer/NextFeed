package com.nextfeed.service.core.question.core.db;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.SharedCoreCacheService;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.question.OptionalQuestionValue;
import com.nextfeed.library.core.valueobject.question.QuestionValue;
import com.nextfeed.library.core.valueobject.question.QuestionValueList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionRepositoryService {

    private final QuestionDBService questionDBService;
    private final VoterDBService voterDBService;
    private final SharedCoreCacheService sharedCoreCacheService;

    private OptionalQuestionValue toValue(QuestionEntity e){
        if(e == null) return OptionalQuestionValue.createByOptionalEntity(Optional.empty(), null, null);
        var p = sharedCoreCacheService.getParticipantById(e.getSession_id(), e.getParticipant_id());
        var voters = voterDBService.getRepo().findByQuestionId(e.getId());
        return OptionalQuestionValue.createByOptionalEntity(Optional.of(e), p.get(), voters);
    }

    public QuestionValue save(QuestionEntity e) {
        e = questionDBService.save(e);
        return toValue(e).get();
    }

    public OptionalQuestionValue findById(Requests.IDRequest request) {
        var e = questionDBService.findById(request.getId());
        return toValue(e.orElse(null));
    }

    public OptionalQuestionValue findById(Integer id) {
        return findById(DTORequestUtils.createIDRequest(id));
    }

    public void addVote(Integer questionId, Integer participantId, Integer rating) {
        voterDBService.save(VoterEntity.builder()
                .question_id(questionId)
                .participant_id(participantId)
                .rating(rating)
                .build());
    }

    public QuestionValueList findBySessionId(int sessionId) {
        var entities = questionDBService.getRepo().findBySessionId(sessionId);
        var values = entities.stream().map(this::toValue).map(OptionalQuestionValue::get).toList();
        return QuestionValueList.createByValues(values);
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        questionDBService.getRepo().deleteAllBySessionId(request.getId());
    }

    public OptionalQuestionValue close(Requests.IDRequest request) {
        var o = questionDBService.findById(request.getId());
        QuestionEntity e = null;
        if(o.isPresent()){
            e = o.get();
            e.setClosed(System.currentTimeMillis());
            questionDBService.save(e);
        }
        return toValue(e);
    }

    public OptionalQuestionValue close(Integer id) {
        return close(DTORequestUtils.createIDRequest(id));
    }


}
