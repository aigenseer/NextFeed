package com.nextfeed.service.manager.session;

import java.util.concurrent.ThreadLocalRandom;

import com.nextfeed.library.core.service.entities.Result;
import com.nextfeed.library.core.service.entities.Student;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class StudentResultCalculator {

    @KafkaListener(topics = "${nextfeed.kafka.relays.student-a.requestTopic}", groupId = "${nextfeed.kafka.relays.student-a.groupId}")
	@SendTo
	public Result handleA(Student student) {
		System.out.println("ACalculating Result...");
		double total = ThreadLocalRandom.current().nextDouble(2.5, 9.9);
		Result result = new Result();
		result.setName(student.getName());
		result.setResult((total > 3.5) ? "Pass" : "Fail");
		result.setPercentage(String.valueOf(total * 10).substring(0, 4) + "%");
		return result;
	}

    @KafkaListener(topics = "${nextfeed.kafka.relays.student-b.requestTopic}", groupId = "${nextfeed.kafka.relays.student-b.groupId}")
    @SendTo
    public Result handleB(Student student) {
        System.out.println("BCalculating Result...");
        double total = ThreadLocalRandom.current().nextDouble(2.5, 9.9);
        Result result = new Result();
        result.setName(student.getName());
        result.setResult((total > 3.5) ? "Pass" : "Fail");
        result.setPercentage(String.valueOf(total * 10).substring(0, 4) + "%");
        return result;
    }
}