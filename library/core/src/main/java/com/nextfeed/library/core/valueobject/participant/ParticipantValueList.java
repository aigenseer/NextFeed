package com.nextfeed.library.core.valueobject.participant;

import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;

import java.util.List;

public class ParticipantValueList extends AbstractValueList<Participant, DTOEntities.ParticipantDTO> {

    public ParticipantValueList(List<ParticipantValue> list) {
        super(list.stream().map(e -> (IValueObject<Participant, DTOEntities.ParticipantDTO>) e).toList());
    }

    public static ParticipantValueList createByEntities(List<Participant> list) {
        var participantValues = list.stream().map(ParticipantValue::new).toList();
        return new ParticipantValueList(participantValues);
    }

    public static ParticipantValueList createByDTO(DTOEntities.ParticipantDTOList dto) {
        var participantValues = dto.getParticipantsList().stream().map(ParticipantValue::createByDTO).toList();
        return new ParticipantValueList(participantValues);
    }

    public DTOEntities.ParticipantDTOList getDTOs(){
        return DTOEntities.ParticipantDTOList.newBuilder().addAllParticipants(getDTOList()).build();
    }



}
