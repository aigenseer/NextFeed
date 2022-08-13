package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<MoodEntity,Integer> {
}
