package com.nextfeed.service.core.question.core.db;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.grpc.service.repository.ParticipantRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.VoteQuestionRequest;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.core.question.ports.incoming.IQuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionRepositoryService implements IQuestionRepositoryService {

    private final QuestionDBService questionDBService;
    private final VoterDBService voterDBService;
    private final ParticipantRepositoryServiceClient participantRepositoryServiceClient;

    private DTOEntities.QuestionDTO toDTO(QuestionEntity e){
        if(e == null) return null;
        var p = participantRepositoryServiceClient.findById(e.getParticipant_id());
        var voters = voterDBService.getRepo().findByQuestionId(e.getId());
        return Entity2DTOUtils.question2DTO(e, p.get(), voters);
    }

    private DTOEntities.OptionalQuestionDTO toOptionalDTO(QuestionEntity e){
        return DTOEntities.OptionalQuestionDTO.newBuilder().setQuestion(toDTO(e)).build();
    }

    public DTOEntities.QuestionDTO save(DTOEntities.QuestionDTO dto) {
        var e = DTO2EntityUtils.dto2Question(dto);
        e = questionDBService.save(e);
        return toDTO(e);
    }

    public DTOEntities.OptionalQuestionDTO findById(Requests.IDRequest request) {
        var e = questionDBService.findById(request.getId());
        return toOptionalDTO(e);
    }

    @Override
    public DTOEntities.OptionalQuestionDTO findById(Integer id) {
        return findById(DTORequestUtils.createIDRequest(id));
    }

    public void addVote(VoteQuestionRequest request) {
        voterDBService.save(VoterEntity.builder()
                .question_id(request.getQuestionId())
                .participant_id(request.getParticipantId())
                .rating(request.getRating())
                .build());
    }

    public void addVote(Integer questionId, Integer participantId, Integer rating) {
        addVote(VoteQuestionRequest.newBuilder().setQuestionId(questionId).setParticipantId(participantId).setRating(rating).build());
    }

    public DTOEntities.QuestionDTOList questionList2DTO(List<QuestionEntity> list){
        return DTOListUtils.toQuestionDTOList(list.stream().map(this::toDTO).toList());
    }

    public DTOEntities.QuestionDTOList findBySessionId(Requests.IDRequest request) {
        var list = questionDBService.getRepo().findBySessionId(request.getId());
        return questionList2DTO(list);
    }

    public void deleteAllBySessionId(Requests.IDRequest request) {
        questionDBService.getRepo().deleteAllBySessionId(request.getId());
    }

    public DTOEntities.OptionalQuestionDTO close(Requests.IDRequest request) {
        var o = questionDBService.findById(request.getId());
        QuestionEntity e = null;
        if(o != null){
            o.setClosed(System.currentTimeMillis());
            questionDBService.save(o);
        }
        return toOptionalDTO(e);
    }

    @Override
    public DTOEntities.OptionalQuestionDTO close(Integer id) {
        return close(DTORequestUtils.createIDRequest(id));
    }


}
