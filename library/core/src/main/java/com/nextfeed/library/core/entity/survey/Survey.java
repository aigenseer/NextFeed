package com.nextfeed.library.core.entity.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
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

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id")
    private Set<SurveyAnswer> surveyAnswers = Set.of();

    @Transactional
    @JsonIgnore
    public List<String> getAnswers(){
        return getSurveyAnswers().stream().map(SurveyAnswer::getValue).toList();
    }

    @ManyToOne
    @JoinColumn(name = "template_id")
    private SurveyTemplate template;
    private long timestamp;
    
    int session_id;

}
