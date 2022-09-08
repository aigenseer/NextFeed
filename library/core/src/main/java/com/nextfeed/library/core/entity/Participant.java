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

    @JoinColumn(referencedColumnName = "Session")
    @JoinColumn(name="session_id", nullable=false)
    Integer session_id;

}