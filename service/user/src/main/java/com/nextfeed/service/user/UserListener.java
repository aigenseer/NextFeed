package com.nextfeed.service.user;

import com.nextfeed.library.core.service.UserService;
import com.nextfeed.library.core.service.entities.NewSessionCreate;
import com.nextfeed.library.core.service.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListener {

    @KafkaListener(topics = "${nextfeed.kafka.topics.userService.backCreate}", groupId = "${nextfeed.kafka.topics.userService.groupId}")
    public void backCreate(User request) {
        System.out.println("Hallo user "+request.getName());
    }

}
