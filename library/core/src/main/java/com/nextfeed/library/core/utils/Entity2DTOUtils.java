package com.nextfeed.library.core.utils;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.question.VoterEntity;
import com.nextfeed.library.core.entity.session.SessionEntity;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyAnswer;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.entity.system.SystemConfiguration;
import com.nextfeed.library.core.entity.user.User;
import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.HashSet;
import java.util.List;

public class Entity2DTOUtils {

    public static DTOEntities.QuestionDTO question2DTO(QuestionEntity q, DTOEntities.ParticipantDTO p, List<VoterEntity> voterEntityList){
        if (q == null) return null;
        var rating = voterEntityList.stream().map(VoterEntity::getRating).mapToInt(Integer::intValue).sum();
        var builder = DTOEntities.QuestionDTO.newBuilder()
                .setId(q.getId())
                .setParticipant(p)
                .setMessage(q.getMessage())
                .setRating(rating)
                .setCreated(q.getCreated())
                .setClosed(q.getClosed())
                .setSessionId(q.getSession_id());
        var dtos = voterEntityList.stream().map(Entity2DTOUtils::voterEntity2DTO).toList();
        builder.addAllVoterEntity(dtos);
        return builder.build();
    }

    public static DTOEntities.VoterEntityDTO voterEntity2DTO(VoterEntity v){
        if (v == null) return null;
        return DTOEntities.VoterEntityDTO.newBuilder()
                .setQuestionId(v.getQuestion_id())
                .setParticipantId(v.getParticipant_id())
                .setRating(v.getRating())
                .build();
    }

    public static DTOEntities.UserDTO user2DTO(User u){
        if (u == null) return null;
        return DTOEntities.UserDTO.newBuilder()
                .setId(u.getId())
                .setName(u.getName())
                .setHashPassword(u.getHashPassword())
                .setRegistrationTime(u.getRegistrationTime())
                .setMailAddress(u.getMailAddress())
                .build();
    }

    public static DTOEntities.ParticipantDTO participant2DTO(Participant p){
        if (p == null) return null;
        return DTOEntities.ParticipantDTO.newBuilder()
                .setId(p.getId())
                .setNickname(p.getNickname())
                .setConnected(p.isConnected())
                .setSessionId(p.getSession_id())
                .build();
    }

    public static DTOEntities.MoodEntityDTO moodEntity2DTO(MoodEntity e){
        if (e == null) return null;
        return DTOEntities.MoodEntityDTO.newBuilder()
                .setId(e.getId())
                .setValue(e.getValue())
                .setTimestamp(e.getTimestamp())
                .setParticipantsCount(e.getParticipantsCount())
                .setSessionId(e.getSession_id())
                .build();
    }

    public static DTOEntities.SystemConfigurationDTO systemConfiguration2DTO(SystemConfiguration s){
        if (s == null) return null;
        return DTOEntities.SystemConfigurationDTO.newBuilder()
                .setId(s.getId())
                .setName(s.getName())
                .setValue(s.getValue())
                .build();
    }


    public static DTOEntities.SurveyTemplateDTO surveyTemplate2DTO(SurveyTemplate e){
        if (e == null) return null;
        return DTOEntities.SurveyTemplateDTO.newBuilder()
                .setId(e.getId())
                .setName(e.getName())
                .setSurveyType(e.getType())
                .setDuration(e.getDuration())
                .setPublishResults(e.isPublishResults())
                .build();
    }

    public static DTOEntities.SurveyAnswerDTO surveyAnswer2DTO(SurveyAnswer e){
        if (e == null) return null;
        return DTOEntities.SurveyAnswerDTO.newBuilder()
                .setId(e.getId())
                .setValue(e.getValue())
                .setSurveyId(e.getSurvey_id())
                .setParticipantId(e.getParticipantId())
                .build();
    }

    public static DTOEntities.SurveyDTO survey2DTO(Survey e){
        if (e == null) return null;
        var builder = DTOEntities.SurveyDTO.newBuilder()
                .setId(e.getId())
                .setTemplate(surveyTemplate2DTO(e.getTemplate()))
                .setTimestamp(e.getTimestamp())
                .setSessionId(e.getSession_id());
        var list = e.getSurveyAnswers().stream().map(Entity2DTOUtils::surveyAnswer2DTO).toList();
        builder.addAllSurveyAnswers(list);
        return builder.build();
    }

    public static DTOEntities.SessionEntityDTO sessionEntity2DTO(SessionEntity e){
        if (e == null) return null;
        return DTOEntities.SessionEntityDTO.newBuilder()
                .setId(e.getId())
                .setName(e.getName())
                .setSessionCode(e.getSessionCode())
                .setClosed(e.getClosed())
                .build();
    }


}
