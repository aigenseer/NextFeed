package com.nextfeed.service.socket.mood;


import com.nextfeed.library.core.service.manager.MoodManagerService;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.security.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@Controller
public class MoodSocketController {

    public static void main(String[] args) {
        SpringApplication.run(MoodSocketController.class, args);
    }

    private final ParticipantManagerService participantManagerService;
    private final MoodManagerService moodManagerService;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/mood-socket/v1/participant/session/{sessionId}/mood/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer rating, Principal principal){
        Optional<Integer> participantId = principalUtils.getClaim("id", principal);
        if(participantId.isPresent()){
            if(participantManagerService.existsParticipantId(participantId.get())){
                moodManagerService.createCalculatedMoodValue(sessionId, NewCalculatedMoodRequest.builder().moodValue(rating).participantId(participantId.get()).build());
            }
        }
    }


}
