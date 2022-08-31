package com.nextfeed.service.socket.session;


import com.nextfeed.library.core.service.manager.MoodManagerService;
import com.nextfeed.library.core.service.manager.ParticipantManagerService;
import com.nextfeed.library.core.service.manager.dto.mood.NewCalculatedMoodRequest;
import com.nextfeed.library.security.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nextfeed", exclude={DataSourceAutoConfiguration.class})
@EnableHystrix
@RequiredArgsConstructor
@Controller
public class SessionSocketController {

    public static void main(String[] args) {
        SpringApplication.run(SessionSocketController.class, args);
    }

    private final ParticipantManagerService participantManagerService;
    private final MoodManagerService moodManagerService;
    private final PrincipalUtils principalUtils;

    @MessageMapping("/socket/session-socket/v1/participant/session/{sessionId}/mood/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer rating, Principal principal){
        Optional<Integer> participantId = principalUtils.getClaim("id", principal);
        if(participantId.isPresent()){
            if(participantManagerService.existsParticipantId(participantId.get())){
                moodManagerService.createCalculatedMoodValue(sessionId, NewCalculatedMoodRequest.builder().moodValue(rating).participantId(participantId.get()).build());
            }
        }
    }


}
