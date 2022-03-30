package com.lecturefeed.model.survey;

import com.lecturefeed.manager.SurveyManager;
import com.lecturefeed.restapi.controller.SurveyRestController;
import com.lecturefeed.socket.controller.service.SurveyService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class SurveyTimer extends Thread{
    private final int sessionId;
    private final Survey survey;
    private final SurveyService surveyService;
    private final SurveyManager surveyManager;

    @Override
    public void run() {
        try {
            sleep(survey.getTemplate().getDuration() * 1000L);
            survey.setTimestamp(new Date().getTime());
            surveyManager.updateSurvey(sessionId, survey);
            surveyService.onClose(sessionId, survey.getId());
            surveyService.onResult(sessionId, survey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
