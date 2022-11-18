package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;
import lombok.Builder;

import java.util.List;

public class ParticipantValueList extends AbstractValueList<Participant, DTOEntities.ParticipantDTO> {

    public ParticipantValueList(List<ParticipantValue> list) {
        super(list.stream().map(e -> (IValueObject<Participant, DTOEntities.ParticipantDTO>) e).toList());
    }

    @Builder(builderMethodName = "builder")
    public static ParticipantValueList newValueDTO(List<Participant> list) {
        return new ParticipantValueList(list.stream().map(ParticipantValue::new).toList());
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static ParticipantValueList newValue(List<DTOEntities.ParticipantDTO> list) {
        return new ParticipantValueList(list.stream().map(ParticipantValue::new).toList());
    }

    public DTOEntities.ParticipantDTOList getDTOs(){
        return DTOEntities.ParticipantDTOList.newBuilder().addAllParticipants(getDTOList()).build();
    }



}
