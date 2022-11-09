package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantManagerServiceClient {

    @GrpcClient("user-management-service")
    private ParticipantManagerServiceGrpc.ParticipantManagerServiceBlockingStub rpcService;

    public DTOEntities.ParticipantDTO createParticipantBySessionId(Integer sessionId, DTOEntities.ParticipantDTO dto) {
        return rpcService.createParticipantBySessionId(CreateParticipantBySessionIdRequest.newBuilder().setSessionId(sessionId).setParticipant(dto).build());
    }

    public DTOEntities.ParticipantDTOList getParticipantsBySessionId(Integer id) {
        return rpcService.getParticipantsBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public DTOEntities.ParticipantDTOList getConnectedParticipantsBySessionId(Integer id) {
        return rpcService.getConnectedParticipantsBySessionId(DTORequestUtils.createIDRequest(id));
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

    public Optional<DTOEntities.ParticipantDTO> getParticipant(Integer id) {
        return Optional.of(rpcService.getParticipant(DTORequestUtils.createIDRequest(id)).getParticipant());
    }

}
