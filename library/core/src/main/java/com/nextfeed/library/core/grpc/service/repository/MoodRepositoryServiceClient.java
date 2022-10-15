package com.nextfeed.library.core.grpc.service.repository;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.MoodRepositoryServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;

public class MoodRepositoryServiceClient {

    @GrpcClient("mood-repository-service")
    private MoodRepositoryServiceGrpc.MoodRepositoryServiceBlockingStub rpcService;


    public DTOEntities.MoodEntityDTO save(DTOEntities.MoodEntityDTO dto) {
        return rpcService.save(dto);
    }

    public DTOEntities.MoodEntityDTOList findBySessionId(Integer id) {
        return rpcService.findBySessionId(DTORequestUtils.createIDRequest(id));
    }


    public void deleteAllBySessionId(Integer id) {
        rpcService.deleteAllBySessionId(DTORequestUtils.createIDRequest(id));
    }

}
