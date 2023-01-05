package com.nextfeed.library.core.service.external;

import com.nextfeed.library.core.proto.manager.ParticipantLoggedOff;
import com.nextfeed.library.core.proto.manager.ParticipantRegistered;
import com.nextfeed.library.core.proto.manager.SessionCreatedRequest;
import com.nextfeed.library.core.proto.manager.SharedCoreServiceGrpc;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.SocketServiceUtils;
import com.nextfeed.library.core.valueobject.participant.ParticipantValue;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@RequiredArgsConstructor
@Service
public class SharedCoreServices {

    private final SocketServiceUtils serviceUtils;

    @Value("#{new Integer('${nextfeed.service.mood-service.grpc-port}')}")
    private Integer mood_port;

    @Value("#{new Integer('${nextfeed.service.question-service.grpc-port}')}")
    private Integer question_port;

    @Value("#{new Integer('${nextfeed.service.survey-service.grpc-port}')}")
    private Integer survey_port;

    @Value("#{new Integer('${nextfeed.service.session-service.grpc-port}')}")
    private Integer session_port;

    private Map<String, Integer> getInstances(){
        return Map.ofEntries(
                entry("mood-service", mood_port),
                entry("question-service", question_port),
                entry("survey-service", survey_port),
                entry("session-service", session_port)
        );
    }

    public List<ManagedChannel> getManagedChannels(){
        var list = new ArrayList<ManagedChannel>();
        for (var entry : getInstances().entrySet()) {
            serviceUtils.getInstanceInfoByName(entry.getKey()).forEach(instance -> {
                try {
                    ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("static://%s:%s".formatted(instance.getIPAddr(), entry.getValue())).usePlaintext().build();
                    list.add(managedChannel);
                }catch (Exception e){
                    System.err.println("Can not call instance");
                    System.err.println(e);
                }
            });
        }
        return list;
    }

    public void sessionCreated(int sessionId, ParticipantValueList participantValueList){
        for (var managedChannel: getManagedChannels()) {
            SharedCoreServiceGrpc.SharedCoreServiceBlockingStub blockingStub = SharedCoreServiceGrpc.newBlockingStub(managedChannel);
            var request = SessionCreatedRequest.newBuilder().setSessionId(sessionId).setParticipantDTOList(participantValueList.getDTOs()).build();
            blockingStub.sessionCreated(request);
            managedChannel.shutdown();
        }
    }

    public void sessionClosed(int sessionId){
        for (var managedChannel: getManagedChannels()) {
            SharedCoreServiceGrpc.SharedCoreServiceBlockingStub blockingStub = SharedCoreServiceGrpc.newBlockingStub(managedChannel);
            var request = DTORequestUtils.createIDRequest(sessionId);
            blockingStub.sessionClosed(request);
            managedChannel.shutdown();
        }
    }

    public void participantRegistered(int sessionId, ParticipantValue participantValue){
        for (var managedChannel: getManagedChannels()) {
            SharedCoreServiceGrpc.SharedCoreServiceBlockingStub blockingStub = SharedCoreServiceGrpc.newBlockingStub(managedChannel);
            var request = ParticipantRegistered.newBuilder().setSessionId(sessionId).setParticipantDTO(participantValue.getDTO()).build();
            blockingStub.participantRegistered(request);
            managedChannel.shutdown();
        }
    }

    public void participantLoggedOff(int sessionId, int participantId){
        for (var managedChannel: getManagedChannels()) {
            SharedCoreServiceGrpc.SharedCoreServiceBlockingStub blockingStub = SharedCoreServiceGrpc.newBlockingStub(managedChannel);
            var request = ParticipantLoggedOff.newBuilder().setSessionId(sessionId).setParticipantId(participantId).build();
            blockingStub.participantLoggedOff(request);
            managedChannel.shutdown();
        }
    }


}
