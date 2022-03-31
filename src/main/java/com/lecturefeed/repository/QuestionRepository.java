package com.lecturefeed.repository;

import com.lecturefeed.entity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
