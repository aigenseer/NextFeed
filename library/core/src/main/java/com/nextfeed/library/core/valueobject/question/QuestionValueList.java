package com.nextfeed.library.core.valueobject.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QuestionValueList {

    private final List<QuestionValue> list;

    @Builder(builderMethodName = "DTOBuilder")
    public static QuestionValueList newValueDTO(DTOEntities.QuestionDTOList dto) {
        return new QuestionValueList(dto.getQuestionsList().stream().map(q -> QuestionValue.DTOBuilder().dto(q).build()).toList());
    }

    @Builder(builderMethodName = "Builder")
    public static QuestionValueList newValue(List<QuestionValue> list) {
        return new QuestionValueList(list);
    }

    public List<QuestionEntity> getEntities(){
        return list.stream().map(QuestionValue::getEntity).toList();
    }

    public DTOEntities.QuestionDTOList getDTOs(){
        return DTOEntities.QuestionDTOList.newBuilder().addAllQuestions(list.stream().map(QuestionValue::getDTO).toList()).build();
    }

}
