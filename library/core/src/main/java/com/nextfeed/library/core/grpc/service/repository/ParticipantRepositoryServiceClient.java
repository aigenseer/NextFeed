package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.ParticipantRepositoryServiceGrpc;
import com.nextfeed.library.core.utils.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantRepositoryServiceClient {

    @GrpcClient("user-management")
    private ParticipantRepositoryServiceGrpc.ParticipantRepositoryServiceBlockingStub rpcService;


    public DTOEntities.ParticipantDTO save(DTOEntities.ParticipantDTO dto) {
        return rpcService.save(dto);
    }

    public Optional<DTOEntities.ParticipantDTO> findById(Integer id) {
        return Optional.of(rpcService.findById(DTORequestUtils.createIDRequest(id)).getParticipant());
    }

    public DTOEntities.ParticipantDTOList findBySessionId(Integer id) {
        return rpcService.findBySessionId(DTORequestUtils.createIDRequest(id));
    }

    public void deleteAllBySessionId(Integer id) {
        rpcService.deleteAllBySessionId(DTORequestUtils.createIDRequest(id));
    }

}
