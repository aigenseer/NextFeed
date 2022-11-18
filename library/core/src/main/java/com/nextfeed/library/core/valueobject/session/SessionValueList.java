package com.nextfeed.library.core.valueobject.session;

import com.nextfeed.library.core.entity.session.SessionContainer;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SessionValueList {

    private final List<SessionValue> list;

    @Builder(builderMethodName = "dtoBuilder")
    public static SessionValueList newValueDTO(List<DTOEntities.SessionDTO> list) {
        return new SessionValueList(list.stream().map(q -> SessionValue.dtoBuilder().dto(q).build()).toList());
    }

    @Builder(builderMethodName = "builder")
    public static SessionValueList newValue(List<SessionValue> list) {
        return new SessionValueList(list);
    }

    public List<SessionContainer> getEntities(){
        return list.stream().map(SessionValue::getEntity).toList();
    }

    public DTOEntities.SessionDTOList getDTOs(){
        return DTOEntities.SessionDTOList.newBuilder().addAllSessions(list.stream().map(SessionValue::getDTO).toList()).build();
    }

}
