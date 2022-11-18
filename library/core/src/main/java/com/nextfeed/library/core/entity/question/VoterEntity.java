package com.nextfeed.library.core.entity.question;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@IdClass(VoterEntity.class)
public class VoterEntity implements Serializable {
    @Id
    @JoinColumn(referencedColumnName = "QuestionEntity")
    @JoinColumn(name="question_id", nullable=false)
    private Integer question_id;
    @Id
    private Integer participant_id;

    private Integer rating;
}
