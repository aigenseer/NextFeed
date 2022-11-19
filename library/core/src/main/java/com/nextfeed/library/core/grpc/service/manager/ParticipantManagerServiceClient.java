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

    public ParticipantValue createParticipantBySessionId(Integer sessionId, DTOEntities.ParticipantDTO dto) {
        return ParticipantValue.createByDTO(rpcService.createParticipantBySessionId(CreateParticipantBySessionIdRequest.newBuilder().setSessionId(sessionId).setParticipant(dto).build()));
    }

    public ParticipantValueList getParticipantsBySessionId(Integer id) {
        var list = rpcService.getParticipantsBySessionId(DTORequestUtils.createIDRequest(id));
        return ParticipantValueList.createByDTO(list);
    }

    public ParticipantValueList getConnectedParticipantsBySessionId(Integer id) {
        return ParticipantValueList.createByDTO(rpcService.getConnectedParticipantsBySessionId(DTORequestUtils.createIDRequest(id)));
    }

    public Optional<DTOEntities.SessionDTO> getSessionByParticipantId(Integer id) {
        return Optional.of(rpcService.getSessionByParticipantId(DTORequestUtils.createIDRequest(id)).getSession());
    }

    public Integer getSessionIdByParticipantId(Integer id) {
        return rpcService.getSessionIdByParticipantId(DTORequestUtils.createIDRequest(id)).getId();
    }

    public void updateConnectionStatusByParticipantId(Integer participantId, Boolean status) {
        rpcService.updateConnectionStatusByParticipantId(UpdateConnectionStatusByParticipantIdRequest.newBuilder().setParticipantId(participantId).setStatus(status).build());
    }

    public boolean existsParticipantId(Integer id) {
        return rpcService.existsParticipantId(DTORequestUtils.createIDRequest(id)).getResult();
    }

    public OptionalParticipantValue getParticipant(Integer id) {
        return OptionalParticipantValue.createByDTO(rpcService.getParticipant(DTORequestUtils.createIDRequest(id)));
    }

}
