package com.nextfeed.library.core.valueobject.surveytemplate;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;
import com.nextfeed.library.core.valueobject.session.SessionValue;
import com.nextfeed.library.core.valueobject.session.SessionValueList;
import com.nextfeed.library.core.valueobject.survey.SurveyValueList;
import lombok.Builder;

import java.util.List;

public class SurveyTemplateValueList extends AbstractValueList<SurveyTemplate, DTOEntities.SurveyTemplateDTO> {

    public SurveyTemplateValueList(List<SurveyTemplateValue> list) {
        super(list.stream().map(e -> (IValueObject<SurveyTemplate, DTOEntities.SurveyTemplateDTO>) e).toList());
    }

    @Builder(builderMethodName = "DTOBuilder")
    public static SurveyTemplateValueList newValueDTO(DTOEntities.SurveyTemplateDTOList dto) {
        return new SurveyTemplateValueList(dto.getSurveyTemplatesList().stream().map(SurveyTemplateValue::new).toList());
    }

    @Builder(builderMethodName = "Builder")
    public static SurveyTemplateValueList newValue(List<SurveyTemplate> list) {
        return new SurveyTemplateValueList(list.stream().map(SurveyTemplateValue::new).toList());
    }

    public DTOEntities.SurveyTemplateDTOList getDTOs(){
        return DTOEntities.SurveyTemplateDTOList.newBuilder().addAllSurveyTemplates(getDTOList()).build();
    }



}
