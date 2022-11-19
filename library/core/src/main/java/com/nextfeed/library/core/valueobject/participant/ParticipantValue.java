package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.question.QuestionValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class ParticipantValue implements IValueObject<Participant, DTOEntities.ParticipantDTO> {

    private final Participant entity;

    ParticipantValue(DTOEntities.ParticipantDTO dto){
        entity = dtoToEntity(dto);
    }

    ParticipantValue(Participant entity){
        this.entity = entity;
    }


    public static ParticipantValue createByDTO(DTOEntities.ParticipantDTO dto) {
        return new ParticipantValue(dto);
    }

    public static ParticipantValue createByEntity(Participant entity) {
        return new ParticipantValue(entity);
    }

    public static Participant dtoToEntity(DTOEntities.ParticipantDTO dto){
        return Participant.builder()
                .id(dto.getId())
                .nickname(dto.getNickname())
                .connected(dto.getConnected())
                .session_id(dto.getSessionId())
                .build();
    }

    public DTOEntities.ParticipantDTO getDTO(){
        return DTOEntities.ParticipantDTO.newBuilder()
                .setId(entity.getId())
                .setNickname(entity.getNickname())
                .setConnected(entity.isConnected())
                .setSessionId(entity.getSession_id())
                .build();
    }

    public Participant getEntity(){
        return entity;
    }

}
