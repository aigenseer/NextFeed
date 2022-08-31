package com.nextfeed.library.core.service.manager.dto.survey;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    private int id;
    public List<String> answers;
    private SurveyTemplate template;
    private long timestamp;
}
