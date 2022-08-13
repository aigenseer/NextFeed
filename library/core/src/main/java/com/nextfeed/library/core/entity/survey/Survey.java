package com.nextfeed.library.core.entity.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey")
    private Set<SurveyAnswer> surveyAnswers = Set.of();

//    @JsonGetter("answers")
//    public List<String> getAnswers(){
//        return surveyAnswers.stream().map(SurveyAnswer::getValue).toList();
//    }

    @ManyToOne
    @JoinColumn(name = "template_id")
    private SurveyTemplate template;
    //the time when the survey was started
    private long timestamp;

}
