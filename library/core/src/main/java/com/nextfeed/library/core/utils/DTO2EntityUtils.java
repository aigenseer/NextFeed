package com.nextfeed.library.core.utils;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.entity.system.SystemConfiguration;
import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.HashSet;

public class DTO2EntityUtils {

    public static MoodEntity dto2MoodEntity(DTOEntities.MoodEntityDTO dto){
        if (dto == null) return null;
        return MoodEntity.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .timestamp(dto.getTimestamp())
                .participantsCount(dto.getParticipantsCount())
                .session_id(dto.getSessionId())
                .build();
    }

    public static Participant dto2Participant(DTOEntities.ParticipantDTO dto){
        if (dto == null) return null;
        return Participant.builder()
                .id(dto.getId())
                .nickname(dto.getNickname())
                .connected(dto.getConnected())
                .session_id(dto.getSessionId())
                .build();
    }

    public static User dto2User(DTOEntities.UserDTO dto){
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .hashPassword(dto.getHashPassword())
                .registrationTime(dto.getRegistrationTime())
                .mailAddress(dto.getMailAddress())
                .build();
    }

    public static SystemConfiguration dto2SystemConfiguration(DTOEntities.SystemConfigurationDTO dto){
        if (dto == null) return null;
        return SystemConfiguration.builder()
                .id(dto.getId())
                .name(dto.getName())
                .value(dto.getValue())
                .build();
    }

    public static QuestionEntity dto2Question(DTOEntities.QuestionDTO dto){
        if (dto == null) return null;
        return QuestionEntity.builder()
                .id(dto.getId())
                .participant_id(dto.getParticipant().getId())
                .message(dto.getMessage())
                .created(dto.getCreated())
                .closed(dto.getClosed())
                .session_id(dto.getSessionId())
                .build();
    }

    public static SurveyTemplate dto2SurveyTemplate(DTOEntities.SurveyTemplateDTO dto){
        if (dto == null) return null;
        return SurveyTemplate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getSurveyType())
                .duration(dto.getDuration())
                .publishResults(dto.getPublishResults())
                .build();
    }

    public static SurveyAnswer dto2SurveyAnswer(DTOEntities.SurveyAnswerDTO dto){
        if (dto == null) return null;
        return SurveyAnswer.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .survey_id(dto.getSurveyId())
                .participantId(dto.getParticipantId())
                .build();
    }

    public static Survey dto2Survey(DTOEntities.SurveyDTO dto){
        if (dto == null) return null;
        return Survey.builder()
                .id(dto.getId())
                .surveyAnswers(new HashSet(dto.getSurveyAnswersList().stream().map(DTO2EntityUtils::dto2SurveyAnswer).toList()))
                .template(dto2SurveyTemplate(dto.getTemplate()))
                .timestamp(dto.getTimestamp())
                .session_id(dto.getSessionId())
                .build();
    }

    public static SessionEntity dto2SessionEntity(DTOEntities.SessionEntityDTO dto){
        if (dto == null) return null;
        return SessionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .closed(dto.getClosed())
                .sessionCode(dto.getSessionCode())
                .build();
    }



}
