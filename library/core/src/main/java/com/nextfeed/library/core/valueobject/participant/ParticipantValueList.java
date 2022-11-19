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

    @Builder(builderMethodName = "Builder")
    public static ParticipantValueList newValue(List<Participant> list) {
        var participantValues = list.stream().map(ParticipantValue::new).toList();
        return new ParticipantValueList(participantValues);
    }

    @Builder(builderMethodName = "DTOBuilder")
    public static ParticipantValueList newValueDTO(DTOEntities.ParticipantDTOList dto) {
        var participantValues = dto.getParticipantsList().stream().map(e -> ParticipantValue.dtoBuilder().dto(e).build()).toList();
        return new ParticipantValueList(participantValues);
    }

    public DTOEntities.ParticipantDTOList getDTOs(){
        return DTOEntities.ParticipantDTOList.newBuilder().addAllParticipants(getDTOList()).build();
    }



}
