package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.CreateParticipantBySessionIdRequest;
import com.nextfeed.library.core.proto.manager.ParticipantManagerServiceGrpc;
import com.nextfeed.library.core.proto.manager.UpdateConnectionStatusByParticipantIdRequest;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.participant.OptionalParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantManagerServiceClient {

    @GrpcClient("user-management-service")
    private ParticipantManagerServiceGrpc.ParticipantManagerServiceBlockingStub rpcService;


    public ParticipantValueList getParticipantsBySessionId(Integer id) {
        var list = rpcService.getParticipantsBySessionId(DTORequestUtils.createIDRequest(id));
        return ParticipantValueList.createByDTO(list);
    }

    public boolean existsParticipantId(Integer id) {
        return rpcService.existsParticipantId(DTORequestUtils.createIDRequest(id)).getResult();
    }

    public OptionalParticipantValue getParticipant(Integer id) {
        return OptionalParticipantValue.createByDTO(rpcService.getParticipant(DTORequestUtils.createIDRequest(id)));
    }

}
