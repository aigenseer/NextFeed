package com.nextfeed.service.manager.session;

import java.util.concurrent.ExecutionException;

import com.nextfeed.service.manager.session.entities.Result;
import com.nextfeed.service.manager.session.entities.Student;
import com.nextfeed.service.manager.session.kafaconfig.initionaliser.ReplyKafkaTemplateAutoConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KafkaController {

    @Value("${nextfeed.kafka.relays.student-a.requestTopic}")
	private String requestTopiA;

    @Value("${nextfeed.kafka.relays.student-b.requestTopic}")
    private String requestTopiB;

    private final ReplyingKafkaTemplate<String, Student, Result> rtkResultA;
    private final ReplyingKafkaTemplate<String, Student, Result> rtkResultB;

	@PostMapping("/get-result-a")
	public ResponseEntity<Result> getObjectA(@RequestBody Student student) throws InterruptedException, ExecutionException {
		ProducerRecord<String, Student> record = new ProducerRecord<>(requestTopiA, null, "STD002", student);
		RequestReplyFuture<String, Student, Result> future = rtkResultA.sendAndReceive(record);
		ConsumerRecord<String, Result> response = future.get();
		return new ResponseEntity<>(response.value(), HttpStatus.OK);
	}

    @PostMapping("/get-result-b")
    public ResponseEntity<Result> getObjectB(@RequestBody Student student) throws InterruptedException, ExecutionException {
        ProducerRecord<String, Student> record = new ProducerRecord<>(requestTopiB, null, "STD002", student);
        RequestReplyFuture<String, Student, Result> future = rtkResultB.sendAndReceive(record);
        ConsumerRecord<String, Result> response = future.get();
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }
}