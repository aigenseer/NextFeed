package com.nextfeed.library.core.valueobject.survey;

import com.nextfeed.library.core.entity.survey.Survey;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.AbstractValueList;
import com.nextfeed.library.core.valueobject.IValueObject;
import lombok.Builder;

import java.util.List;

public class SurveyValueList extends AbstractValueList<Survey, DTOEntities.SurveyDTO> {

    public SurveyValueList(List<SurveyValue> list) {
        super(list.stream().map(e -> (IValueObject<Survey, DTOEntities.SurveyDTO>) e).toList());
    }

    @Builder(builderMethodName = "dtoBuilder")
    public static SurveyValueList newValueDTO(List<DTOEntities.SurveyDTO> list) {
        return new SurveyValueList(list.stream().map(SurveyValue::new).toList());
    }

    @Builder(builderMethodName = "builder")
    public static SurveyValueList newValue(List<Survey> list) {
        return new SurveyValueList(list.stream().map(SurveyValue::new).toList());
    }

    public DTOEntities.SurveyDTOList getDTOs(){
        return DTOEntities.SurveyDTOList.newBuilder().addAllSurveys(getDTOList()).build();
    }



}