package com.nextfeed.library.core.entity.survey;


import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SurveyTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private DTOEntities.SurveyType type;
    private String question;
    //always >0
    private long duration;
    private boolean publishResults;
}
