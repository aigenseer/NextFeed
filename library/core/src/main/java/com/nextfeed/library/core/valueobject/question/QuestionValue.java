package com.nextfeed.library.core.valueobject.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

public class QuestionValue implements IValueObject<QuestionEntity, DTOEntities.QuestionDTO> {

    private final QuestionEntity entity;
    private final Optional<DTOEntities.QuestionDTO> dto;
    private final List<VoterEntity> voterEntityList;
    private final ParticipantValue participantValue;

    QuestionValue(DTOEntities.QuestionDTO dto){
        entity = dtoToEntity(dto);
        this.participantValue = null;
        this.voterEntityList = null;
        this.dto = Optional.of(dto);
    }

    QuestionValue(QuestionEntity entity, ParticipantValue participantValue, List<VoterEntity> voterEntityList){
        this.entity = entity;
        this.participantValue = participantValue;
        this.voterEntityList = voterEntityList;
        this.dto = Optional.empty();
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static QuestionValue newValue(DTOEntities.QuestionDTO dto) {
        return new QuestionValue(dto);
    }

    @Builder(builderMethodName = "builder")
    public static QuestionValue newValue(QuestionEntity entity, ParticipantValue participantValue, List<VoterEntity> voterEntityList) {
        return new QuestionValue(entity, participantValue, voterEntityList);
    }

    private QuestionEntity dtoToEntity(DTOEntities.QuestionDTO dto){
        return QuestionEntity.builder()
                .id(dto.getId())
                .participant_id(dto.getParticipant().getId())
                .message(dto.getMessage())
                .created(dto.getCreated())
                .closed(dto.getClosed())
                .session_id(dto.getSessionId())
                .build();
    }

    public DTOEntities.QuestionDTO getDTO(){
        if(dto.isPresent()){
            return dto.get();
        }
        var rating = voterEntityList.stream().map(VoterEntity::getRating).mapToInt(Integer::intValue).sum();
        var builder = DTOEntities.QuestionDTO.newBuilder()
                .setId(entity.getId())
                .setParticipant(participantValue.getDTO())
                .setMessage(entity.getMessage())
                .setRating(rating)
                .setCreated(entity.getCreated())
                .setClosed(entity.getClosed())
                .setSessionId(entity.getSession_id());
        var dtos = voterEntityList.stream().map(Entity2DTOUtils::voterEntity2DTO).toList();
        builder.addAllVoterEntity(dtos);
        return builder.build();
    }

    public QuestionEntity getEntity(){
        return entity;
    }

}
