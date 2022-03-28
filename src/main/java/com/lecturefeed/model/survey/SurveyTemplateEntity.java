package com.lecturefeed.model.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO: add @Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyTemplateEntity {
    private SurveyType surveyType;
    private String question;
    //always >0
    private long duration;
    private boolean publishResults;
    private int id;
}
