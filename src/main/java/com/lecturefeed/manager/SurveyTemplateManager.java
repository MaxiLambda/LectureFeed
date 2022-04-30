package com.lecturefeed.manager;

import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.repository.service.SurveyTemplateDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SurveyTemplateManager {

    private final SurveyTemplateDBService surveyTemplateDBService;

    public SurveyTemplate createTemplate(SurveyTemplate template){
        return surveyTemplateDBService.save(template);
    }

    public void checkTemplateId(int templateId){
        if (surveyTemplateDBService.findById(templateId)==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Template-Id %d are not exists", templateId));
    }

    public Collection<SurveyTemplate> getAllTemplates(){
        return surveyTemplateDBService.findAll();
    }

    public SurveyTemplate getTemplateById(int templateId){
        return surveyTemplateDBService.findById(templateId);
    }

}
