package com.nextfeed.service.core.session.core.db;

import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.grpc.service.manager.MoodManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.ParticipantManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.QuestionManagerServiceClient;
import com.nextfeed.library.core.grpc.service.manager.SurveyManagerServiceClient;
import com.nextfeed.library.core.valueobject.session.OptionalSessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValueList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SessionRepositoryService {

    private final SessionDBService sessionDBService;
    private final ParticipantManagerServiceClient participantManagerServiceClient;
    private final QuestionManagerServiceClient questionManagerServiceClient;
    private final SurveyManagerServiceClient surveyManagerServiceClient;
    private final MoodManagerServiceClient moodManagerServiceClient;

    private SessionValue createValueByEntity(SessionEntity entity){
        var sessionId = entity.getId();
        return SessionValue.builder()
                .entity(entity)
                .participantValues(participantManagerServiceClient.getParticipantsBySessionId(sessionId))
                .questionValues(questionManagerServiceClient.findBySessionId(sessionId))
                .surveyValues(surveyManagerServiceClient.getSurveysBySessionId(sessionId))
                .moodValues(moodManagerServiceClient.findBySessionId(sessionId))
                .build();
    }

    public SessionValue save(SessionEntity entity){
        entity = sessionDBService.save(entity);
        return createValueByEntity(entity);
    }

    public void deleteById(Integer id) {
        sessionDBService.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return sessionDBService.existsById(id);
    }

    public SessionValueList findAll() {
        var pList = sessionDBService.findAll().stream().map(this::createValueByEntity).toList();
        return SessionValueList.builder().list(pList).build();
    }

    public SessionValueList findAllOpen() {
        var pList = sessionDBService.findAllOpen().stream().map(this::createValueByEntity).toList();
        return SessionValueList.builder().list(pList).build();
    }

    public SessionValueList findAllClosed() {
        var pList = sessionDBService.findAllClosed().stream().map(this::createValueByEntity).toList();
        return SessionValueList.builder().list(pList).build();
    }

    public OptionalSessionValue findById(Integer id) {
        var optionalEntity = sessionDBService.findById(id);
        Optional<SessionValue> optionalValue = optionalEntity.map(this::createValueByEntity);
        return OptionalSessionValue.builder().source(optionalValue).build();
    }

}
