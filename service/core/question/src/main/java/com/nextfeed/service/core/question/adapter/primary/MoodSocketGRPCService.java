package com.nextfeed.service.core.question.adapter.primary;

import com.nextfeed.library.core.proto.repository.MoodSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import core.MoodDataService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class MoodSocketGRPCService extends MoodSocketServiceGrpc.MoodSocketServiceImplBase {

    private final MoodDataService sessionDataService;

    @Override
    public void sendMood(SendMoodRequest request, StreamObserver<Response.Empty> responseObserver) {
        sessionDataService.sendMood(request.getSessionId(), request.getValue());
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }


}
