package com.nextfeed.library.core.entity.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    private String value;

    @JoinColumn(referencedColumnName = "Session")
    @JoinColumn(name="survey_id", nullable=false)
    private Integer survey_id;

    private Integer participantId;
}
