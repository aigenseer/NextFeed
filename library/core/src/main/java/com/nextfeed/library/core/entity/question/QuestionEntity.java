package com.nextfeed.library.core.entity.question;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer participant_id;
    private String message;
    private Long created;
    private Boolean anonymous;
    private Long closed;

    int session_id;

}