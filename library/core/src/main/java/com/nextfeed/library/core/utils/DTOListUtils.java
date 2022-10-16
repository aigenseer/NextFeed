package com.nextfeed.library.core.utils;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;

import java.util.List;

public class DTOListUtils {

    public static DTOEntities.ParticipantDTOList toParticipantDTOList(List<DTOEntities.ParticipantDTO> list){
        return DTOEntities.ParticipantDTOList.newBuilder().addAllParticipants(list).build();
    }

    public static DTOEntities.ParticipantDTOList participantList2DTO(List<Participant> list){
        return toParticipantDTOList(list.stream().map(Entity2DTOUtils::participant2DTO).toList());
    }

    public static DTOEntities.QuestionDTOList toQuestionDTOList(List<DTOEntities.QuestionDTO> list){
        return DTOEntities.QuestionDTOList.newBuilder().addAllQuestions(list).build();
    }

    public static DTOEntities.SurveyTemplateDTOList surveyTemplateList2DTO(List<SurveyTemplate> list){
        return toSurveyTemplateDTOList(list.stream().map(Entity2DTOUtils::surveyTemplate2DTO).toList());
    }

    public static DTOEntities.SurveyTemplateDTOList toSurveyTemplateDTOList(List<DTOEntities.SurveyTemplateDTO> list){
        return DTOEntities.SurveyTemplateDTOList.newBuilder().addAllSurveyTemplates(list).build();
    }

    public static DTOEntities.SurveyDTOList surveyList2DTO(List<Survey> list){
        return toSurveyDTOList(list.stream().map(Entity2DTOUtils::survey2DTO).toList());
    }

    public static DTOEntities.SurveyDTOList toSurveyDTOList(List<DTOEntities.SurveyDTO> list){
        return DTOEntities.SurveyDTOList.newBuilder().addAllSurveys(list).build();
    }

    public static DTOEntities.MoodEntityDTOList moodEntities2DTO(List<MoodEntity> list){
        return toMoodEntityList(list.stream().map(Entity2DTOUtils::moodEntity2DTO).toList());
    }

    public static DTOEntities.MoodEntityDTOList toMoodEntityList(List<DTOEntities.MoodEntityDTO> list){
        return DTOEntities.MoodEntityDTOList.newBuilder().addAllEntries(list).build();
    }

    public static DTOEntities.SessionDTOList toSessionDTOList(List<DTOEntities.SessionDTO> list){
        return DTOEntities.SessionDTOList.newBuilder().addAllSessions(list).build();
    }

}
