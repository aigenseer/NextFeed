package com.nextfeed.library.core.adapter.primary.grpc.sharedcore;

import com.nextfeed.library.core.entity.participant.Participant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Builder
public class SessionCache {
    @Getter
    private final int sessionId;
    @Getter
    private final ConcurrentHashMap<Integer, Participant> participants = new ConcurrentHashMap<>();


    public final boolean existsParticipantById(Integer participantId){
        return participants.containsKey(participantId);
    }

    public final void addParticipant(Participant participant){
        participants.put(participant.getId(), participant);
    }

    public final void removeParticipantById(Integer participantId){
        participants.remove(participantId);
    }

}
