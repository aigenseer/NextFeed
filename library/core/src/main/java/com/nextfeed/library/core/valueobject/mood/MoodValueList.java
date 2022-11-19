package com.nextfeed.library.core.valueobject.mood;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;
import lombok.Builder;

import java.util.List;

public class MoodValueList extends AbstractValueList<MoodEntity, DTOEntities.MoodEntityDTO> {

    public MoodValueList(List<MoodValue> list) {
        super(list.stream().map(e -> (IValueObject<MoodEntity, DTOEntities.MoodEntityDTO>) e).toList());
    }

    @Builder(builderMethodName = "DTOBuilder")
    public static MoodValueList newValueDTO(DTOEntities.MoodEntityDTOList dto) {
        return new MoodValueList(dto.getEntriesList().stream().map(MoodValue::new).toList());
    }

    @Builder(builderMethodName = "Builder")
    public static MoodValueList newValue(List<MoodEntity> list) {
        return new MoodValueList(list.stream().map(MoodValue::new).toList());
    }

    public DTOEntities.MoodEntityDTOList getDTOs(){
        return DTOEntities.MoodEntityDTOList.newBuilder().addAllEntries(getDTOList()).build();
    }



}
