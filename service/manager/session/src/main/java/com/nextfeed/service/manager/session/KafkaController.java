package com.nextfeed.service.manager.session;

import java.util.concurrent.ExecutionException;

import com.nextfeed.library.core.service.SessionManagerService;
import com.nextfeed.library.core.service.entities.Result;
import com.nextfeed.library.core.service.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class KafkaController {

    private final SessionManagerService sessionManagerService;

	@PostMapping("/get-result-a")
	public ResponseEntity<Result> getObjectA(@RequestBody Student student) throws InterruptedException, ExecutionException {
		return new ResponseEntity<>(sessionManagerService.getObjectA(student), HttpStatus.OK);
	}

    @PostMapping("/get-result-b")
    public ResponseEntity<Result> getObjectB(@RequestBody Student student) throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(sessionManagerService.getObjectB(student), HttpStatus.OK);
    }


}