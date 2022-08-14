package com.nextfeed.service.manager.session;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    private final StreamBridge streamBridge;

    @PostMapping
    public void publish(@RequestBody MessageRequest request){
        streamBridge.send("producer-out-0",new Message(request.message()));

    }

}
