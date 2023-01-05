package com.nextfeed.service.core.mood.adapter.primary;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.MoodManagerServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.service.core.mood.ports.incoming.IMoodManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class MoodManagerGRPCService extends MoodManagerServiceGrpc.MoodManagerServiceImplBase {

    private final IMoodManager moodManager;

    @Override
    public void findBySessionId(Requests.IDRequest request, StreamObserver<DTOEntities.MoodEntityDTOList> responseObserver) {
        var list = moodManager.findBySessionId(request.getId());
        responseObserver.onNext(list.getDTOs());
        responseObserver.onCompleted();
    }

}
