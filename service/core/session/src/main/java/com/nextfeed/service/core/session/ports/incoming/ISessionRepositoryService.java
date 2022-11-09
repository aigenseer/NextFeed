package com.nextfeed.service.core.session.ports.incoming;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.requests.Requests;

public interface ISessionRepositoryService {

    DTOEntities.SessionDTO save(DTOEntities.SessionEntityDTO dto);

    void deleteById(Requests.IDRequest request);

    void deleteById(Integer id);

    DTOEntities.SessionDTOList findAll();

    DTOEntities.SessionDTOList findAllOpen();

    DTOEntities.SessionDTOList findAllClosed();

    DTOEntities.OptionalSessionDTO findById(Requests.IDRequest request);

    DTOEntities.OptionalSessionDTO findById(Integer id);

    DTOEntities.OptionalSessionEntityDTO findEntityById(Requests.IDRequest request);

    DTOEntities.OptionalSessionEntityDTO findEntityById(Integer id);

    DTOEntities.OptionalSessionEntityDTO close(Requests.IDRequest request);

    DTOEntities.OptionalSessionEntityDTO close(Integer id);

}
