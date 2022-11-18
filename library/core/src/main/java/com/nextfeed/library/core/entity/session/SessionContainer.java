package com.nextfeed.library.core.entity.session;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.QuestionEntity;
import com.nextfeed.library.core.entity.survey.Survey;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionContainer {

    private int id;
    private List<Participant> participants;
    private List<QuestionEntity> questions;
    private List<Survey> surveys;
    private List<MoodEntity> moodEntities;
    private long closed;
    private String sessionCode;
    private String name;

}