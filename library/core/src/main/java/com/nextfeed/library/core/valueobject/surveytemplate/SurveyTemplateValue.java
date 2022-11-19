package com.nextfeed.library.core.valueobject.surveytemplate;

import com.nextfeed.library.core.entity.survey.SurveyTemplate;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import com.nextfeed.library.core.valueobject.IValueObject;

public class SurveyTemplateValue implements IValueObject<SurveyTemplate, DTOEntities.SurveyTemplateDTO> {

    private final SurveyTemplate entity;

    SurveyTemplateValue(DTOEntities.SurveyTemplateDTO dto){
        entity = dtoToEntity(dto);
    }

    SurveyTemplateValue(SurveyTemplate entity){
        this.entity = entity;
    }

    public static SurveyTemplateValue createByDTO(DTOEntities.SurveyTemplateDTO dto) {
        return new SurveyTemplateValue(dto);
    }

    public static SurveyTemplateValue createByEntity(SurveyTemplate entity) {
        return new SurveyTemplateValue(entity);
    }

    public static SurveyTemplate dtoToEntity(DTOEntities.SurveyTemplateDTO dto){
        return SurveyTemplate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getSurveyType())
                .duration(dto.getDuration())
                .publishResults(dto.getPublishResults())
                .build();
    }

    public DTOEntities.SurveyTemplateDTO getDTO(){
        return DTOEntities.SurveyTemplateDTO.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setSurveyType(entity.getType())
                .setDuration(entity.getDuration())
                .setPublishResults(entity.isPublishResults())
                .build();
    }

    public SurveyTemplate getEntity(){
        return entity;
    }

}
