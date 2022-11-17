package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;

@Builder
public class ParticipantValue implements IValueObject<Participant, DTOEntities.ParticipantDTO> {

    private final Participant entity;

    ParticipantValue(DTOEntities.ParticipantDTO dto){
        entity = dtoToEntity(dto);
    }

    ParticipantValue(Participant entity){
        this.entity = entity;
    }

    private Participant dtoToEntity(DTOEntities.ParticipantDTO dto){
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
