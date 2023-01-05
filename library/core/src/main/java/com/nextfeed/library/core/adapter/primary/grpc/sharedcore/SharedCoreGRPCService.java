package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.ParticipantLoggedOffEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.ParticipantRegisteredEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.SessionClosedEvent;
import com.nextfeed.library.core.adapter.primary.grpc.sharedcore.event.SessionCreatedEvent;
import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.manager.ParticipantLoggedOff;
import com.nextfeed.library.core.proto.manager.ParticipantRegistered;
import com.nextfeed.library.core.proto.manager.SessionCreatedRequest;
import com.nextfeed.library.core.proto.manager.SharedCoreServiceGrpc;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.proto.response.Response;
import com.nextfeed.library.core.utils.DTOResponseUtils;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@GrpcService
public class SharedCoreGRPCService extends SharedCoreServiceGrpc.SharedCoreServiceImplBase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SessionManagerServiceClient sessionManagerServiceClient;

    @PostConstruct
    public void init(){
        try {
            var response = sessionManagerServiceClient.getShareCache();
            for (var shareCache :response.getShareCachesList()) {
                var event = new SessionCreatedEvent(this, shareCache.getSessionId(), ParticipantValueList.createByDTO(shareCache.getParticipantDTOList()));
                applicationEventPublisher.publishEvent(event);
            }
        }catch (Exception e){
            System.out.println("Failed to fetch initial share cache");
        }
    }

    @Override
    public void sessionCreated(SessionCreatedRequest request, StreamObserver<Response.Empty> responseObserver) {
        var event = new SessionCreatedEvent(this, request.getSessionId(), ParticipantValueList.createByDTO(request.getParticipantDTOList()));
        applicationEventPublisher.publishEvent(event);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void sessionClosed(Requests.IDRequest request, StreamObserver<Response.Empty> responseObserver) {
        var event = new SessionClosedEvent(this, request.getId());
        applicationEventPublisher.publishEvent(event);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void participantRegistered(ParticipantRegistered request, StreamObserver<Response.Empty> responseObserver) {
        var event = new ParticipantRegisteredEvent(this, request.getSessionId(), ParticipantValue.createByDTO(request.getParticipantDTO()));
        applicationEventPublisher.publishEvent(event);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

    @Override
    public void participantLoggedOff(ParticipantLoggedOff request, StreamObserver<Response.Empty> responseObserver) {
        var event = new ParticipantLoggedOffEvent(this, request.getSessionId(), request.getParticipantId());
        applicationEventPublisher.publishEvent(event);
        responseObserver.onNext(DTOResponseUtils.createEmpty());
        responseObserver.onCompleted();
    }

}
