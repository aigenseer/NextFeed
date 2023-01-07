package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.proto.repository.MoodSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.utils.MicroserviceUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoodSocketServices {

    private final MicroserviceUtils serviceUtils;
    private final static String INSTANCE_NAME = "mood-service";

    @Value("#{new Integer('${nextfeed.service.mood-service.grpc-port}')}")
    private Integer port;

    public void sendMood(Integer sessionId, Double value){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
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
