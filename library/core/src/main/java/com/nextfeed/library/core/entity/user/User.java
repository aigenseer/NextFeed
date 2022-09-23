package com.nextfeed.library.core.entity.user;

import com.nextfeed.library.core.entity.survey.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "NextFeedUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String hashPassword;
    private long registrationTime;
    private String mailAddress;

}
