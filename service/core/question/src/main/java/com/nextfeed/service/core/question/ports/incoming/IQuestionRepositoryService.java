package com.nextfeed.service.core.question.ports.incoming;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.VoteQuestionRequest;
import com.nextfeed.library.core.proto.requests.Requests;

import java.util.List;

public interface IQuestionRepositoryService {

    DTOEntities.QuestionDTO save(DTOEntities.QuestionDTO dto);

    DTOEntities.OptionalQuestionDTO findById(Requests.IDRequest request);

    DTOEntities.OptionalQuestionDTO findById(Integer id);

    void addVote(VoteQuestionRequest request);

    void addVote(Integer questionId, Integer participantId, Integer rating);

    DTOEntities.QuestionDTOList questionList2DTO(List<QuestionEntity> list);

    DTOEntities.QuestionDTOList findBySessionId(Requests.IDRequest request);

    void deleteAllBySessionId(Requests.IDRequest request);

    DTOEntities.OptionalQuestionDTO close(Requests.IDRequest request);

    DTOEntities.OptionalQuestionDTO close(Integer id);

}
