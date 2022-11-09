package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.manager.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class MoodManagerServiceClient {

    @GrpcClient("mood-service")
    private MoodManagerServiceGrpc.MoodManagerServiceBlockingStub rpcService;

    public DTOEntities.MoodEntityDTO addMoodValueToSession(Integer sessionId, Double moodValue, Integer participantCount) {
        var request = NewMoodRequest.newBuilder().setMoodValue(moodValue).setParticipantsCount(participantCount).build();
        return rpcService.addMoodValueToSession(AddMoodValueToSessionRequest.newBuilder().setSessionId(sessionId).setNewMoodRequest(request).build());
    }

    public DTOEntities.MoodEntityDTO createCalculatedMoodValue(Integer sessionId, Double moodValue, Integer participantId) {
        var request = NewCalculatedMoodRequest.newBuilder().setMoodValue(moodValue).setParticipantId(participantId).build();
        return rpcService.createCalculatedMoodValue(CreateCalculatedMoodValueRequest.newBuilder().setSessionId(sessionId).setNewCalculatedMoodRequest(request).build());
    }

}
