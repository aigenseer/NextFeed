package com.nextfeed.service.core.mood.adapter.primary;

import com.nextfeed.library.core.proto.repository.MoodSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.service.core.mood.ports.incoming.IMoodSocketService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class MoodSocketGRPCService extends MoodSocketServiceGrpc.MoodSocketServiceImplBase {

    private final IMoodSocketService moodSocketService;

    @Override
    public void sendMood(SendMoodRequest request, StreamObserver<Response.Empty> responseObserver) {
        moodSocketService.sendMood(request.getSessionId(), request.getValue());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}