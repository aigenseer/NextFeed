package com.nextfeed.library.core.entity.question;

import com.nextfeed.library.core.entity.participant.Participant;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private Integer id;
    private Participant participant;
    private String message;
    private Integer rating = 0;
    private Long created;
    private Boolean anonymous;
    private Long closed;

    private Set<Integer> voters = Set.of();
    int session_id;

}