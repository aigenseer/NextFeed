package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.*;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessionSocketServices {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "session-socket-service";

    @Value("#{new Integer('${nextfeed.service.session-service.grpc-port}')}")
    private Integer port;

    public void sendNewParticipantToAll(Integer sessionId, ParticipantValue participantValue){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SessionSocketServiceGrpc.SessionSocketServiceBlockingStub blockingStub = SessionSocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.sendNewParticipantToAll(SendNewParticipantToAllRequest.newBuilder().setSessionId(sessionId).setParticipant(participantValue.getDTO()).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void sendClose(Integer sessionId){
        String path = "/api/internal/session-socket/v1/session/%d/notify/session/close".formatted(sessionId);
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SessionSocketServiceGrpc.SessionSocketServiceBlockingStub blockingStub = SessionSocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.sendClose(DTORequestUtils.createIDRequest(sessionId));
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

    public void sendConnectionStatus(Integer sessionId, List<DTOEntities.ParticipantDTO> participantDTOs){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                SessionSocketServiceGrpc.SessionSocketServiceBlockingStub blockingStub = SessionSocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.sendConnectionStatus(SendConnectionStatusRequest.newBuilder().setSessionId(sessionId).setParticipants(DTOListUtils.toParticipantDTOList(participantDTOs)).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
