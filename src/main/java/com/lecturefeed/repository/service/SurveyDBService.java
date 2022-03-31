package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.repository.SurveyRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyDBService extends AbstractService<Survey, SurveyRepository> {
    public SurveyDBService(SurveyRepository surveyRepository) {
        super(surveyRepository);
    }
}
