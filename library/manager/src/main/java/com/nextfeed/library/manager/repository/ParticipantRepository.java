package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.Participant;
import com.nextfeed.library.core.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {

    List<Participant> findBySession(Session session);
}
