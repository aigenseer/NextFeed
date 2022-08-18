
package com.nextfeed.library.core.service;

import com.nextfeed.library.core.service.entities.Result;
import com.nextfeed.library.core.service.entities.Student;
import com.nextfeed.library.core.service.entities.User;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${nextfeed.kafka.topics.userService.backCreate}")
    private String sendUserTopic;

    private final KafkaTemplate<String, User> kafkaTemplate;

    public void sendUser(){
        kafkaTemplate.send(sendUserTopic, User.builder().name("UserName").build());
    }


}
