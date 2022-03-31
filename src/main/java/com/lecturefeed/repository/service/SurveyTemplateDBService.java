package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.repository.SurveyTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class SurveyTemplateDBService extends AbstractService<SurveyTemplate, SurveyTemplateRepository> {
    public SurveyTemplateDBService(SurveyTemplateRepository surveyTemplateRepository) {
        super(surveyTemplateRepository);
    }

}
