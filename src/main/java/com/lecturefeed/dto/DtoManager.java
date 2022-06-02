package com.lecturefeed.dto;

import com.lecturefeed.entity.model.survey.SurveyTemplate;
import org.springframework.stereotype.Service;

@Service
public class DtoManager {
    public SurveyTemplate getSurveyTemplate(SurveyTemplateDto dto){
        return SurveyTemplate.builder().
                id(dto.getId()).
                name(dto.getName()).
                type(dto.getType()).
                question(dto.getQuestion()).
                duration(dto.getDuration()).
                publishResults(dto.isPublishResults()).
                build();
    }
}
