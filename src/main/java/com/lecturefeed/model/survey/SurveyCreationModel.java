package com.lecturefeed.model.survey;

import lombok.Data;

@Data
public class SurveyCreationModel {
    private final SurveyTemplateEntity surveyTemplateEntity;
    private final int id;
}
