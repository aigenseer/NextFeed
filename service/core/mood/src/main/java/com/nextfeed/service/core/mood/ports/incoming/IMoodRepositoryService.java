package com.nextfeed.service.core.mood.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.requests.Requests;

public interface IMoodRepositoryService {


    DTOEntities.MoodEntityDTO save(DTOEntities.MoodEntityDTO dto);

    DTOEntities.MoodEntityDTOList findBySessionId(Requests.IDRequest request);

    void deleteAllBySessionId(Requests.IDRequest request);

}
