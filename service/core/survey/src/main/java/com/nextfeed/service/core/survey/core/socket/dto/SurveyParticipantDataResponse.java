package com.nextfeed.service.core.survey.core.socket.dto;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyParticipantDataResponse {
    private int surveyId;
    private SurveyTemplate surveyTemplate;
}
