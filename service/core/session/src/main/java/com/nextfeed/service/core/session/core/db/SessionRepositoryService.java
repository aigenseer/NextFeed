package com.nextfeed.service.core.session.core.db;

import com.nextfeed.library.core.grpc.service.repository.MoodRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.ParticipantRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.QuestionRepositoryServiceClient;
import com.nextfeed.library.core.grpc.service.repository.SurveyRepositoryServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.proto.requests.Requests;
import com.nextfeed.library.core.utils.DTO2EntityUtils;
import com.nextfeed.library.core.utils.DTOListUtils;
import com.nextfeed.library.core.utils.DTORequestUtils;
import com.nextfeed.library.core.utils.Entity2DTOUtils;
import com.nextfeed.service.core.session.ports.incoming.ISessionDBService;
import com.nextfeed.service.core.session.ports.incoming.ISessionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class SessionRepositoryService implements ISessionRepositoryService {

    private final ISessionDBService sessionDBService;
    private final ParticipantRepositoryServiceClient participantRepositoryServiceClient;
    private final QuestionRepositoryServiceClient questionRepositoryServiceClient;
    private final SurveyRepositoryServiceClient surveyRepositoryServiceClient;
    private final MoodRepositoryServiceClient moodRepositoryServiceClient;

    private DTOEntities.SessionDTO mapEntityToDTO(DTOEntities.SessionEntityDTO dto){
        return DTOEntities.SessionDTO.newBuilder()
                .setId(dto.getId())
                .setParticipants(participantRepositoryServiceClient.findBySessionId(dto.getId()))
                .setQuestions(questionRepositoryServiceClient.findBySessionId(dto.getId()))
                .setSurveys(surveyRepositoryServiceClient.findBySessionId(dto.getId()))
                .setMoodEntities(moodRepositoryServiceClient.findBySessionId(dto.getId()))
                .setClosed(dto.getClosed())
                .setSessionCode(dto.getSessionCode())
                .setName(dto.getName())
                .build();
    }

    public DTOEntities.SessionDTO save(DTOEntities.SessionEntityDTO dto){
        var e = DTO2EntityUtils.dto2SessionEntity(dto);
        e = sessionDBService.save(e);
        dto = Entity2DTOUtils.sessionEntity2DTO(e);
        return mapEntityToDTO(dto);
    }

    public void deleteById(Requests.IDRequest request) {
        sessionDBService.deleteById(request.getId());
    }

    @Override
    public void deleteById(Integer id) {
        deleteById(DTORequestUtils.createIDRequest(id));
    }


    public DTOEntities.SessionDTOList findAll() {
        var pList = sessionDBService.findAll().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        return DTOListUtils.toSessionDTOList(pList);
    }

    public DTOEntities.SessionDTOList findAllOpen() {
        var pList = sessionDBService.findAllOpen().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        return DTOListUtils.toSessionDTOList(pList);
    }

    public DTOEntities.SessionDTOList findAllClosed() {
        var pList = sessionDBService.findAllClosed().stream().map(Entity2DTOUtils::sessionEntity2DTO).map(this::mapEntityToDTO).toList();
        return DTOListUtils.toSessionDTOList(pList);
    }

    public DTOEntities.OptionalSessionDTO findById(Requests.IDRequest request) {
        var e = sessionDBService.findById(request.getId());
        var session = mapEntityToDTO(Entity2DTOUtils.sessionEntity2DTO(e));
        return DTOEntities.OptionalSessionDTO.newBuilder().setSession(session).build();
    }

    @Override
    public DTOEntities.OptionalSessionDTO findById(Integer id) {
        return findById(DTORequestUtils.createIDRequest(id));
    }

    public DTOEntities.OptionalSessionEntityDTO findEntityById(Requests.IDRequest request) {
        var e = sessionDBService.findById(request.getId());
        return DTOEntities.OptionalSessionEntityDTO.newBuilder().setSessionEntity(Entity2DTOUtils.sessionEntity2DTO(e)).build();
    }

    @Override
    public DTOEntities.OptionalSessionEntityDTO findEntityById(Integer id) {
        return findEntityById(DTORequestUtils.createIDRequest(id));
    }


    public DTOEntities.OptionalSessionEntityDTO close(Requests.IDRequest request) {
        DTOEntities.SessionEntityDTO dto = null;
        var e = sessionDBService.findById(request.getId());
        if (e != null){
            e.setClosed(new Date().getTime());
            e = sessionDBService.save(e);
            dto = Entity2DTOUtils.sessionEntity2DTO(e);
        }
        return DTOEntities.OptionalSessionEntityDTO.newBuilder().setSessionEntity(dto).build();
    }

    @Override
    public DTOEntities.OptionalSessionEntityDTO close(Integer id) {
        return close(DTORequestUtils.createIDRequest(id));
    }

}
