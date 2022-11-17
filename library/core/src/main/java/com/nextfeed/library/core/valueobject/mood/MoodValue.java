package com.nextfeed.library.core.valueobject.mood;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;
import lombok.Builder;

@Builder
public class MoodValue implements IValueObject<MoodEntity, DTOEntities.MoodEntityDTO> {

    private final MoodEntity entity;

    MoodValue(DTOEntities.MoodEntityDTO dto){
        entity = dtoToEntity(dto);
    }
    MoodValue(MoodEntity entity){
        this.entity = entity;
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static MoodValue newValue(DTOEntities.MoodEntityDTO dto) {
        return new MoodValue(dto);
    }

    private MoodEntity dtoToEntity(DTOEntities.MoodEntityDTO dto){
        return MoodEntity.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .timestamp(dto.getTimestamp())
                .participantsCount(dto.getParticipantsCount())
                .session_id(dto.getSessionId())
                .build();
    }

    public DTOEntities.MoodEntityDTO getDTO(){
        return DTOEntities.MoodEntityDTO.newBuilder()
                .setId(entity.getId())
                .setValue(entity.getValue())
                .setTimestamp(entity.getTimestamp())
                .setParticipantsCount(entity.getParticipantsCount())
                .setSessionId(entity.getSession_id())
                .build();
    }

    public MoodEntity getEntity(){
        return entity;
    }

}
