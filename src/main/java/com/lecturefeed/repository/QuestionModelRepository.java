package com.lecturefeed.repository;

import com.lecturefeed.entity.model.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionModelRepository extends JpaRepository<QuestionModel, Integer> {
}
