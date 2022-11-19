package com.nextfeed.library.core.valueobject.question;

import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QuestionValueList {

    private final List<QuestionValue> list;

    public static QuestionValueList createByDTO(DTOEntities.QuestionDTOList dto) {
        return new QuestionValueList(dto.getQuestionsList().stream().map(QuestionValue::createByDTO).toList());
    }

    public static QuestionValueList createByValues(List<QuestionValue> list) {
        return new QuestionValueList(list);
    }

    public List<QuestionEntity> getEntities(){
        return list.stream().map(QuestionValue::getEntity).toList();
    }

    public DTOEntities.QuestionDTOList getDTOs(){
        return DTOEntities.QuestionDTOList.newBuilder().addAllQuestions(list.stream().map(QuestionValue::getDTO).toList()).build();
    }

}
