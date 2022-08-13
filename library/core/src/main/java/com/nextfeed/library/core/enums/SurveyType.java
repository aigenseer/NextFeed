package com.nextfeed.library.core.enums;

import lombok.Getter;

public enum SurveyType {
    YesNo(0),
    Rating(1),
    OpenAnswer(2);

    @Getter
    private final int type;

    SurveyType(int type){
        this.type = type;
    }
}

