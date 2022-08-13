package com.nextfeed.library.manager.repository;


import com.nextfeed.library.core.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
