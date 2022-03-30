package com.lecturefeed.model.survey;

import com.lecturefeed.entity.model.survey.SurveyTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyParticipantData {
    private int surveyId;
    private SurveyTemplate surveyTemplate;

}
