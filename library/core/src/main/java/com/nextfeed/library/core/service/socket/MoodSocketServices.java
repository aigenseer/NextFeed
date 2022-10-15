package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.proto.repository.MoodSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoodSocketServices {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "mood-socket-service";

    @Value("#{new Integer('${nextfeed.service.mood-socket-service.port}')}")
    private Integer port;

    public void sendMood(Integer sessionId, Double value){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(instance.getIPAddr(), port).usePlaintext().build();
                MoodSocketServiceGrpc.MoodSocketServiceBlockingStub blockingStub = MoodSocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.sendMood(SendMoodRequest.newBuilder().setSessionId(sessionId).setValue(value).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
