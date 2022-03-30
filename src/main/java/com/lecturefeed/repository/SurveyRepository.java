package com.lecturefeed.repository;

import com.lecturefeed.entity.model.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey,Integer> {
}
