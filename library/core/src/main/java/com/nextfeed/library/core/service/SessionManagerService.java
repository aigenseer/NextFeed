package com.nextfeed.library.core.service;

import com.nextfeed.library.core.service.entities.Result;
import com.nextfeed.library.core.service.entities.Student;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class SessionManagerService {

    @Value("${nextfeed.kafka.relays.student-a.requestTopic}")
    private String requestTopiA;

    @Value("${nextfeed.kafka.relays.student-b.requestTopic}")
    private String requestTopiB;

    private final ReplyingKafkaTemplate<String, Student, Result> rtkResultA;
    private final ReplyingKafkaTemplate<String, Student, Result> rtkResultB;


    public Result getObjectA(Student student) throws InterruptedException, ExecutionException {
        ProducerRecord<String, Student> record = new ProducerRecord<>(requestTopiA, null, "STD002", student);
        RequestReplyFuture<String, Student, Result> future = rtkResultA.sendAndReceive(record);
        ConsumerRecord<String, Result> response = future.get();
        return response.value();
    }


    public Result getObjectB(@RequestBody Student student) throws InterruptedException, ExecutionException {
        ProducerRecord<String, Student> record = new ProducerRecord<>(requestTopiB, null, "STD002", student);
        RequestReplyFuture<String, Student, Result> future = rtkResultB.sendAndReceive(record);
        ConsumerRecord<String, Result> response = future.get();
        return response.value();
    }


}
