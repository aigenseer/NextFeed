package com.nextfeed.library.core.valueobject.mood;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;

public class MoodValue implements IValueObject<MoodEntity, DTOEntities.MoodEntityDTO> {

    private final MoodEntity entity;

    MoodValue(DTOEntities.MoodEntityDTO dto){
        entity = dtoToEntity(dto);
    }
    MoodValue(MoodEntity entity){
        this.entity = entity;
    }

    public static MoodValue createByDTO(DTOEntities.MoodEntityDTO dto) {
        return new MoodValue(dto);
    }

    public static MoodValue createByEntity(MoodEntity entity) {
        return new MoodValue(entity);
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
