package com.nextfeed.library.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    double value;
    long timestamp;
    int participantsCount;

    @JoinColumn(referencedColumnName = "Session")
    @JoinColumn(name="session_id", nullable=false)
    int session_id;

}