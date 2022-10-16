package com.nextfeed.service.socket.survey.dto;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyParticipantDataResponse {
    private int surveyId;
    private SurveyTemplate surveyTemplate;
}
