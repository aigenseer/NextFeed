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

    //null means the question is not yet handled by the server
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //null means the question was send anonymous
    //TODO maybe create Relation between id and participant
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;
    private String message;
    private Integer rating = 0;
    private Long created;
    private Boolean anonymous;
    //null means the question was never closed
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

//    @Column(name = "session_id", nullable=false)
//    int session_id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "session_id", insertable=false, updatable=false)
//    private Session session;

}
