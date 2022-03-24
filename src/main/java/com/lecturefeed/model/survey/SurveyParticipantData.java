package com.lecturefeed.model.survey;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyParticipantData {
    private int surveyId;
    private SurveyTemplate surveyTemplate;

}
