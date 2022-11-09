package com.nextfeed.service.core.mood.adapter.primary;


import com.nextfeed.library.core.grpc.service.manager.MoodManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.proto.manager.NewCalculatedMoodRequest;
import com.nextfeed.library.security.utils.PrincipalUtils;
import com.nextfeed.service.core.mood.ports.incoming.IMoodManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
public class MoodSocketController {

    private final ParticipantManagerServiceClient participantManagerServiceClient;
    private final IMoodManager moodManagerServiceClient;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/mood-socket/v1/participant/session/{sessionId}/mood/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer rating, Principal principal){
        Optional<Integer> participantId = principalUtils.getClaim("id", principal);
        if(participantId.isPresent()){
            if(participantManagerServiceClient.existsParticipantId(participantId.get())){
                moodManagerServiceClient.createCalculatedMoodValue(sessionId, NewCalculatedMoodRequest.newBuilder().setMoodValue(Double.valueOf(rating)).setParticipantId(participantId.get()).build());
            }
        }
    }


}