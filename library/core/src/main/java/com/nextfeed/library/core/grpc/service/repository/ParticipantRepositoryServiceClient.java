package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ParticipantRepositoryServiceGrpc;
import com.nextfeed.library.core.utils.*;
import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantRepositoryServiceClient {

    @GrpcClient("user-management")
    private ParticipantRepositoryServiceGrpc.ParticipantRepositoryServiceBlockingStub rpcService;


    public ParticipantValue save(DTOEntities.ParticipantDTO dto) {
        return ParticipantValue.dtoBuilder().dto(dto).build();
    }

    public OptionalParticipantValue findById(Integer id) {
        return OptionalParticipantValue.dtoBuilder().dto(rpcService.findById(DTORequestUtils.createIDRequest(id))).build();
    }

    public ParticipantValueList findBySessionId(Integer id) {
        return ParticipantValueList.createByDTOs(rpcService.findBySessionId(DTORequestUtils.createIDRequest(id)).getParticipantsList());
    }

    public void deleteAllBySessionId(Integer id) {
        rpcService.deleteAllBySessionId(DTORequestUtils.createIDRequest(id));
    }

}
