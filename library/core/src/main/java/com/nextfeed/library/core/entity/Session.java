package com.nextfeed.library.core.entity;

import com.nextfeed.library.core.entity.survey.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "session")
    private Set<Participant> participants = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Question> questions = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Survey> surveys = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<MoodEntity> moodEntities = Set.of();

    private long closed = 0;
    private String sessionCode;
    private String name;

}
