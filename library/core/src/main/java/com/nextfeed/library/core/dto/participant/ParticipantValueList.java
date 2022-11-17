package com.nextfeed.library.core.dto.participant;

import com.nextfeed.library.core.dto.AbstractValueList;
import com.nextfeed.library.core.dto.IValueObject;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.List;

public class ParticipantValueList extends AbstractValueList<Participant, DTOEntities.ParticipantDTO> {

    public ParticipantValueList(List<ParticipantValue> list) {
        super(list.stream().map(e -> (IValueObject<Participant, DTOEntities.ParticipantDTO>) e).toList());
    }

    public static ParticipantValueList createByEntities(List<Participant> list) {
        return new ParticipantValueList(list.stream().map(ParticipantValue::new).toList());
    }

    public static ParticipantValueList createByDTOs(List<DTOEntities.ParticipantDTO> list) {
        return new ParticipantValueList(list.stream().map(ParticipantValue::new).toList());
    }

    public DTOEntities.ParticipantDTOList getDTOs(){
        return DTOEntities.ParticipantDTOList.newBuilder().addAllParticipants(getDTOList()).build();
    }



}
