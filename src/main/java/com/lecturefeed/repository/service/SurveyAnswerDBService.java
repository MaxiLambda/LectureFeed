package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.survey.SurveyAnswer;
import com.lecturefeed.repository.SurveyAnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyAnswerDBService extends AbstractService<SurveyAnswer, SurveyAnswerRepository> {
    public SurveyAnswerDBService(SurveyAnswerRepository surveyAnswerRepository) {
        super(surveyAnswerRepository);
    }
}
