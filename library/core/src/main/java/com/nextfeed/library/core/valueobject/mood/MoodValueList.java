package com.nextfeed.library.core.valueobject.mood;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;

import java.util.List;

public class MoodValueList extends AbstractValueList<MoodEntity, DTOEntities.MoodEntityDTO> {

    public MoodValueList(List<MoodValue> list) {
        super(list.stream().map(e -> (IValueObject<MoodEntity, DTOEntities.MoodEntityDTO>) e).toList());
    }

    public static MoodValueList createByEntities(List<MoodEntity> list) {
        return new MoodValueList(list.stream().map(MoodValue::new).toList());
    }

    public static MoodValueList createByDTOs(List<DTOEntities.MoodEntityDTO> list) {
        return new MoodValueList(list.stream().map(MoodValue::new).toList());
    }

    public DTOEntities.MoodEntityDTOList getDTOs(){
        return DTOEntities.MoodEntityDTOList.newBuilder().addAllEntries(getDTOList()).build();
    }



}
