package com.nextfeed.library.core.valueobject.session;

import com.nextfeed.library.core.entity.session.SessionContainer;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SessionValueList {

    private final List<SessionValue> list;

    public static SessionValueList createByDTO(DTOEntities.SessionDTOList dto) {
        return new SessionValueList(dto.getSessionsList().stream().map(SessionValue::createByDTO).toList());
    }

    public static SessionValueList createByValues(List<SessionValue> list) {
        return new SessionValueList(list);
    }

    public List<SessionContainer> getEntities(){
        return list.stream().map(SessionValue::getEntity).toList();
    }

    public DTOEntities.SessionDTOList getDTOs(){
        return DTOEntities.SessionDTOList.newBuilder().addAllSessions(list.stream().map(SessionValue::getDTO).toList()).build();
    }

}
