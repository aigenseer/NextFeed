package com.nextfeed.service.core.mood.adapter.primary;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.service.core.mood.core.MoodManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class MoodManagerGRPCService extends MoodManagerServiceGrpc.MoodManagerServiceImplBase {

    private final MoodManager moodManager;

    @Override
    public void addMoodValueToSession(AddMoodValueToSessionRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var dto = moodManager.addMoodValueToSession(request.getSessionId(), request.getNewMoodRequest());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

    @Override
    public void createCalculatedMoodValue(CreateCalculatedMoodValueRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var dto = moodManager.createCalculatedMoodValue(request.getSessionId(), request.getNewCalculatedMoodRequest());
        responseObserver.onNext(dto);
        responseObserver.onCompleted();
    }

}
