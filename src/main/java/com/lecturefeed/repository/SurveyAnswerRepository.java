package com.lecturefeed.repository;

import com.lecturefeed.entity.model.survey.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer,Integer> {
}
