package com.nextfeed.library.core.entity.question;

import com.nextfeed.library.core.entity.participant.Participant;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {

    private Integer id;
    private Participant participant;
    private String message;
    private Integer rating = 0;
    private Long created;
    private Boolean anonymous;
    private Long closed;

    private List<VoterEntity> voters;
    int session_id;

}