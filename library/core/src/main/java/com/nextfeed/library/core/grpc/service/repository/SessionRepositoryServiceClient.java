package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.SessionRepositoryServiceGrpc;
import com.nextfeed.library.core.utils.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionRepositoryServiceClient {

    @GrpcClient("session-service")
    private SessionRepositoryServiceGrpc.SessionRepositoryServiceBlockingStub rpcService;

    public DTOEntities.SessionDTO save(DTOEntities.SessionEntityDTO dto) {
        return rpcService.save(dto);
    }

    public void deleteById(Integer id) {
        rpcService.deleteById(DTORequestUtils.createIDRequest(id));
    }

    public DTOEntities.SessionDTOList findAll() {
        return rpcService.findAll(DTOResponseUtils.createEmpty());
    }

    public DTOEntities.SessionDTOList findAllOpen() {
        return rpcService.findAllOpen(DTOResponseUtils.createEmpty());
    }

    public DTOEntities.SessionDTOList findAllClosed() {
        return rpcService.findAllClosed(DTOResponseUtils.createEmpty());
    }


    public Optional<DTOEntities.SessionDTO> findById(Integer id) {
        return Optional.of(rpcService.findById(DTORequestUtils.createIDRequest(id)).getSession());
    }

    public Optional<DTOEntities.SessionEntityDTO> findEntityById(Integer id) {
        return Optional.of(rpcService.findEntityById(DTORequestUtils.createIDRequest(id)).getSessionEntity());
    }

    public Optional<DTOEntities.SessionEntityDTO> close(Integer id) {
        return Optional.of(rpcService.close(DTORequestUtils.createIDRequest(id)).getSessionEntity());
    }

}
