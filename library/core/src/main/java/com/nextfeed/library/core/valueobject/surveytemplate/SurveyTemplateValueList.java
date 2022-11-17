package com.nextfeed.library.core.valueobject.surveytemplate;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;

import java.util.List;

public class SurveyTemplateValueList extends AbstractValueList<SurveyTemplate, DTOEntities.SurveyTemplateDTO> {

    public SurveyTemplateValueList(List<SurveyTemplateValue> list) {
        super(list.stream().map(e -> (IValueObject<SurveyTemplate, DTOEntities.SurveyTemplateDTO>) e).toList());
    }

    public static SurveyTemplateValueList createByEntities(List<SurveyTemplate> list) {
        return new SurveyTemplateValueList(list.stream().map(SurveyTemplateValue::new).toList());
    }

    public static SurveyTemplateValueList createByDTOs(List<DTOEntities.SurveyTemplateDTO> list) {
        return new SurveyTemplateValueList(list.stream().map(SurveyTemplateValue::new).toList());
    }

    public DTOEntities.SurveyTemplateDTOList getDTOs(){
        return DTOEntities.SurveyTemplateDTOList.newBuilder().addAllSurveyTemplates(getDTOList()).build();
    }



}
