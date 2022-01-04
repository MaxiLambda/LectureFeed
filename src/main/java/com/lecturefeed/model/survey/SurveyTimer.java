package com.lecturefeed.model.survey;

import com.lecturefeed.socket.controller.service.SurveyService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class SurveyTimer extends Thread{
    private final SurveyEntity surveyEntity;
    private final int sessionId;
    //the List of all the published surveys of the corresponding session
    private final ArrayList<Integer> publishedSurveys;
    private final SurveyService surveyService;

    @Override
    public void run() {
        try {
            sleep(surveyEntity.getDuration() * 1000L);
            //publish only if the survey was not published manually
            if(!publishedSurveys.contains(surveyEntity.getId())){
                publishedSurveys.add(surveyEntity.getId());
                surveyService.onClose(sessionId, surveyEntity.getId());
                surveyService.onResult(sessionId, surveyEntity);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
