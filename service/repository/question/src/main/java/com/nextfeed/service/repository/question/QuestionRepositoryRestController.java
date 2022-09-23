package com.nextfeed.service.repository.question;

import com.nextfeed.library.core.entity.question.Question;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.service.repository.ParticipantRepositoryService;
import com.nextfeed.library.core.service.repository.QuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableFeignClients(basePackages = "com.nextfeed.library.core.service")
@EntityScan("com.nextfeed.library.core.entity.question")
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.nextfeed")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/question-repository")
public class QuestionRepositoryRestController implements QuestionRepositoryService {

    public static void main(String[] args) {
        SpringApplication.run(QuestionRepositoryRestController.class, args);
    }

    private final QuestionDBService questionDBService;
    private final ParticipantRepositoryService participantRepositoryService;

    private Question mapEntityToDTO(QuestionEntity q){
        return Question.builder()
                .id(q.getId())
                .participant(participantRepositoryService.findById(q.getParticipant_id()))
                .message(q.getMessage())
                .rating(q.getRating())
                .created(q.getCreated())
                .closed(q.getClosed())
                .voters(q.getVoters())
                .session_id(q.getSession_id())
                .build();
    }

    @RequestMapping(value = "/v1/save", method = RequestMethod.POST)
    public Question save(@RequestBody QuestionEntity question) {
        return mapEntityToDTO(questionDBService.save(question));
    }

    @RequestMapping(value = "/v1/get/id/{questionId}", method = RequestMethod.GET)
    public QuestionEntity findById(@PathVariable("questionId") Integer questionId) {
        return questionDBService.findById(questionId);
    }

    @RequestMapping(value = "/v1/get/all/{sessionId}", method = RequestMethod.GET)
    public List<Question> findBySessionId(@PathVariable("sessionId") Integer sessionId) {
        return questionDBService.getRepo().findBySessionId(sessionId).stream().map(this::mapEntityToDTO).toList();
    }

    @RequestMapping(value = "/v1/delete/all/{sessionId}", method = RequestMethod.GET)
    public void deleteAllBySessionId(@PathVariable("sessionId") Integer sessionId) {
        questionDBService.getRepo().deleteAllBySessionId(sessionId);
    }

}
