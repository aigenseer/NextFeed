package com.nextfeed.library.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;
    private String message;
    private Integer rating = 0;
    private Long created;
    private Boolean anonymous;
    private Long closed;

    //TODO implement possibility to map voters to ids
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> voters = Set.of();

    @JoinColumn(referencedColumnName = "Session")
    @JoinColumn(name="session_id", nullable=false)
    int session_id;

}