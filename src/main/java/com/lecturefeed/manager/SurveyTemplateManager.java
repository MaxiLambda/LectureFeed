package com.lecturefeed.manager;

import com.lecturefeed.model.survey.SurveyTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SurveyTemplateManager {

    private final HashMap<Integer, SurveyTemplate> templates = new HashMap<>();

    public SurveyTemplate createTemplate(SurveyTemplate template){
        template.setId(templates.size()+1);
        templates.put(template.getId(), template);
        return template;
    }

    public void checkTemplateId(int templateId){
        if (!templates.containsKey(templateId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Template-Id %d are not exists", templateId));
    }

    public Collection<SurveyTemplate> getAllTemplates(){
        return templates.values();
    }

    public SurveyTemplate getTemplateById(int templateId){
        return templates.get(templateId);
    }

}
