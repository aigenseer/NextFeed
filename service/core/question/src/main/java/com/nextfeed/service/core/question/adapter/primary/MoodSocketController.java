package com.nextfeed.service.core.question.adapter.primary;


import com.nextfeed.library.core.grpc.service.manager.MoodManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.security.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;


@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@Controller
public class MoodSocketController {

    public static void main(String[] args) {
        SpringApplication.run(MoodSocketController.class, args);
    }

    private final ParticipantManagerServiceClient participantManagerServiceClient;
    private final MoodManagerServiceClient moodManagerServiceClient;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/mood-socket/v1/participant/session/{sessionId}/mood/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer rating, Principal principal){
        Optional<Integer> participantId = principalUtils.getClaim("id", principal);
        if(participantId.isPresent()){
            if(participantManagerServiceClient.existsParticipantId(participantId.get())){
                moodManagerServiceClient.createCalculatedMoodValue(sessionId, Double.valueOf(rating), participantId.get());
            }
        }
    }


}
