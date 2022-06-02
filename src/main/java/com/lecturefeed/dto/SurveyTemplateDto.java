package com.lecturefeed.dto;

import com.lecturefeed.model.survey.SurveyType;
import lombok.Getter;

@Getter
public class SurveyTemplateDto {
    private int id;
    private String name;
    private SurveyType type;
    private String question;
    private long duration;
    private boolean publishResults;
}
