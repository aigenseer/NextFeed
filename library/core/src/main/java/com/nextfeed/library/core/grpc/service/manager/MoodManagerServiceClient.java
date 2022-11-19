package com.nextfeed.library.core.grpc.service.manager;

import com.nextfeed.library.core.proto.manager.*;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.valueobject.mood.MoodValue;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class MoodManagerServiceClient {

    @GrpcClient("mood-service")
    private MoodManagerServiceGrpc.MoodManagerServiceBlockingStub rpcService;

    public MoodValue addMoodValueToSession(Integer sessionId, Double moodValue, Integer participantCount) {
        var request = NewMoodRequest.newBuilder().setMoodValue(moodValue).setParticipantsCount(participantCount).build();
        var dto = rpcService.addMoodValueToSession(AddMoodValueToSessionRequest.newBuilder().setSessionId(sessionId).setNewMoodRequest(request).build());
        return MoodValue.createByDTO(dto);
    }

    public MoodValue createCalculatedMoodValue(Integer sessionId, Double moodValue, Integer participantId) {
        var request = NewCalculatedMoodRequest.newBuilder().setMoodValue(moodValue).setParticipantId(participantId).build();
        var dto = rpcService.createCalculatedMoodValue(CreateCalculatedMoodValueRequest.newBuilder().setSessionId(sessionId).setNewCalculatedMoodRequest(request).build());
        return MoodValue.createByDTO(dto);
    }

    public MoodValueList findBySessionId(Integer id) {
        return MoodValueList.createByDTO(rpcService.findBySessionId(DTORequestUtils.createIDRequest(id)));
    }



}
