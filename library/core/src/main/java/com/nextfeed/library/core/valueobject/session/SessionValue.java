package com.nextfeed.library.core.valueobject.session;

import com.nextfeed.library.core.entity.session.SessionContainer;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.mood.MoodValueList;
import com.nextfeed.library.core.valueobject.participant.ParticipantValueList;
import com.nextfeed.library.core.valueobject.question.QuestionValueList;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import lombok.Builder;

import java.util.Optional;

public class SessionValue implements IValueObject<SessionContainer, DTOEntities.SessionDTO> {

    private final SessionContainer source;
    private final SessionEntity entity;
    private final Optional<DTOEntities.SessionDTO> dto;
    private final ParticipantValueList participantValues;
    private final QuestionValueList questionValues;
    private final MoodValueList moodValues;
    private final SurveyValueList surveyValues;

    SessionValue(DTOEntities.SessionDTO dto){
        source = dtoToSource(dto);
        this.participantValues = null;
        this.questionValues = null;
        this.moodValues = null;
        this.surveyValues = null;
        this.entity = null;
        this.dto = Optional.of(dto);
    }

    SessionValue(SessionEntity entity,
                 ParticipantValueList participantValues,
                 QuestionValueList questionValues,
                 MoodValueList moodValues,
                 SurveyValueList surveyValues){
        this.entity = entity;
        this.participantValues = participantValues;
        this.questionValues = questionValues;
        this.moodValues = moodValues;
        this.surveyValues = surveyValues;
        this.dto = Optional.empty();
        this.source = createSource();
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static SessionValue newValue(DTOEntities.SessionDTO dto) {
        return new SessionValue(dto);
    }

    @Builder(builderMethodName = "builder")
    public static SessionValue newValue(SessionEntity entity,
                                        ParticipantValueList participantValues,
                                        QuestionValueList questionValues,
                                        MoodValueList moodValues,
                                        SurveyValueList surveyValues) {
        return new SessionValue(entity, participantValues, questionValues, moodValues, surveyValues);
    }

    private SessionContainer dtoToSource(DTOEntities.SessionDTO dto){
        return SessionContainer.builder()
                .id(dto.getId())
                .closed(dto.getClosed())
                .name(dto.getName())
                .sessionCode(dto.getSessionCode())
                .participants(ParticipantValueList.dtoBuilder().list(dto.getParticipants().getParticipantsList()).build().getEntities())
                .questions(QuestionValueList.dtoBuilder().list(dto.getQuestions().getQuestionsList()).build().getEntities())
                .moodEntities(MoodValueList.dtoBuilder().list(dto.getMoodEntities().getEntriesList()).build().getEntities())
                .surveys(SurveyValueList.dtoBuilder().list(dto.getSurveys().getSurveysList()).build().getEntities())
                .build();
    }

    private SessionContainer createSource(){
        var builder = SessionContainer.builder();
        builder.id(entity.getId());
        builder.closed(entity.getClosed());
        builder.name(entity.getName());
        builder.sessionCode(entity.getSessionCode());
        builder.participants(participantValues.getEntities());
        builder.questions(questionValues.getEntities());
        builder.moodEntities(moodValues.getEntities());
        builder.surveys(surveyValues.getEntities());
        return builder.build();
    }

    public DTOEntities.SessionDTO getDTO(){
        if(dto.isPresent()){
            return dto.get();
        }
        var builder = DTOEntities.SessionDTO.newBuilder();
        builder.setId(entity.getId());
        builder.setClosed(entity.getClosed());
        builder.setName(entity.getName());
        builder.setSessionCode(entity.getSessionCode());
        builder.setParticipants(participantValues.getDTOs());
        builder.setQuestions(questionValues.getDTOs());
        builder.setMoodEntities(moodValues.getDTOs());
        builder.setSurveys(surveyValues.getDTOs());
        return builder.build();
    }

    public SessionContainer getEntity(){
        return source;
    }

}
