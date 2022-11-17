package com.nextfeed.service.core.mood.adapter.primary;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.AddMoodValueToSessionRequest;
import com.nextfeed.library.core.proto.manager.CreateCalculatedMoodValueRequest;
import com.nextfeed.library.core.proto.manager.MoodManagerServiceGrpc;
import com.nextfeed.service.core.mood.ports.incoming.IMoodManager;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class MoodManagerGRPCService extends MoodManagerServiceGrpc.MoodManagerServiceImplBase {

    private final IMoodManager moodManager;

    @Override
    public void addMoodValueToSession(AddMoodValueToSessionRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var moodValue = moodManager.addMoodValueToSession(request.getSessionId(), request.getNewMoodRequest());
        responseObserver.onNext(moodValue.getDTO());
        responseObserver.onCompleted();
    }

    @Override
    public void createCalculatedMoodValue(CreateCalculatedMoodValueRequest request, StreamObserver<DTOEntities.MoodEntityDTO> responseObserver) {
        var moodValue = moodManager.createCalculatedMoodValue(request.getSessionId(), request.getNewCalculatedMoodRequest());
        responseObserver.onNext(moodValue.getDTO());
        responseObserver.onCompleted();
    }

}
