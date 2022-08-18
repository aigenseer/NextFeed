package com.nextfeed.service.manager.session;

import com.nextfeed.library.core.service.UserService;
import com.nextfeed.library.core.service.entities.NewSessionCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionListener {

    private final UserService userService;

    @KafkaListener(concurrency = "2", topics = "${nextfeed.kafka.topics.sessionManager.sessionCreate}"/*, groupId = "${nextfeed.kafka.topics.sessionManager.groupId}"*/)
    public void sessionCreate(NewSessionCreate request) {
        System.out.println("Start: Hallo session "+request.getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End: Hallo session "+request.getName());
        userService.sendUser();
    }


}
