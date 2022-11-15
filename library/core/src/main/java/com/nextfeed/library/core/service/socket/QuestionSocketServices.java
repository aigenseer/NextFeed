package com.nextfeed.library.core.service.socket;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.repository.MoodSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.QuestionSocketServiceGrpc;
import com.nextfeed.library.core.proto.repository.SendMoodRequest;
import com.nextfeed.library.core.proto.repository.SendQuestionRequest;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionSocketServices {

    private final SocketServiceUtils serviceUtils;
    private final static String INSTANCE_NAME = "question-service";

    @Value("#{new Integer('${nextfeed.service.question-service.grpc-port}')}")
    private Integer port;

    public void sendQuestion(Integer sessionId, DTOEntities.QuestionDTO questionDTO){
        serviceUtils.getInstanceInfoByName(INSTANCE_NAME).forEach(instance -> {
            try {
                ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), port)).usePlaintext().build();
                QuestionSocketServiceGrpc.QuestionSocketServiceBlockingStub blockingStub = QuestionSocketServiceGrpc.newBlockingStub(managedChannel);
                blockingStub.sendQuestion(SendQuestionRequest.newBuilder().setSessionId(sessionId).setQuestionDTO(questionDTO).build());
                managedChannel.shutdown();
            }catch (Exception e){
                System.err.println("Can not call instance");
                System.err.println(e);
            }
        });
    }

}
