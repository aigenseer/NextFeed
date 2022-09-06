package com.nextfeed.library.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;
    private boolean connected = false;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="session_id", nullable=false)
//    private Session session;


//    int session_id;
//
////    @JoinColumn(referencedColumnName = "Session")
//    @JsonIgnore
//    @JoinColumn(name="session_id", nullable=false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Session session;


    @JoinColumn(referencedColumnName = "Session")
    @JoinColumn(name="session_id", nullable=false)
    Integer session_id;



}
