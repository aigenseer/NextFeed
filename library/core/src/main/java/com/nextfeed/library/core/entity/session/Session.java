package com.nextfeed.library.core.entity.session;

import com.nextfeed.library.core.entity.mood.MoodEntity;
import com.nextfeed.library.core.entity.participant.Participant;
import com.nextfeed.library.core.entity.question.Question;
import com.nextfeed.library.core.entity.survey.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private List<Participant> participants = List.of();

    private List<Question> questions = List.of();

    private List<Survey> surveys = List.of();

    private List<MoodEntity> moodEntities = List.of();

    private long closed = 0;
    private String sessionCode;
    private String name;

}