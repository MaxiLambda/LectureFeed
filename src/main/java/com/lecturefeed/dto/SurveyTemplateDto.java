package com.lecturefeed.dto;

import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyType;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class SurveyTemplateDto {
    private int id;
    private String name;
    private SurveyType type;
    private String question;
    private long duration;
    private boolean publishResults;
}
