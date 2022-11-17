package com.nextfeed.library.core.valueobject.question;

import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
public class QuestionValueList {

    private final List<QuestionValue> list;

    public DTOEntities.QuestionDTOList getDTOs(){
        return DTOEntities.QuestionDTOList.newBuilder().addAllQuestions(list.stream().map(QuestionValue::getDTO).toList()).build();
    }

}
