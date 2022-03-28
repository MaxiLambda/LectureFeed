package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.MessageModel;
import com.lecturefeed.model.token.ParticipantClaims;
import com.lecturefeed.model.token.TokenModel;
import com.lecturefeed.model.survey.SurveyCreationModel;
import com.lecturefeed.model.survey.SurveyEntity;
import com.lecturefeed.model.survey.SurveyTemplateEntity;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.socket.controller.service.SurveyService;
import com.lecturefeed.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class SurveyController {

    private final SurveyService surveyService;
    private final CustomAuthenticationService customAuthenticationService;

    private final HashMap<Integer, ArrayList<SurveyEntity>> sessionSurveys = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> publishedSessionSurveys = new HashMap<>();

    public SurveyController(SurveyService surveyService, CustomAuthenticationService customAuthenticationService){
        this.surveyService = surveyService;
        this.customAuthenticationService = customAuthenticationService;
    }

    @PostMapping("/admin/session/{sessionId}/survey/create")
    public void create(@PathVariable int sessionId, @RequestBody SurveyTemplateEntity surveyTemplateEntity){
        SurveyCreationModel surveyCreationModel = createSurveyInSession(sessionId,surveyTemplateEntity);
        surveyService.onCreate(sessionId,surveyCreationModel);
    }


    @PostMapping("/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageModel messageModel, @RequestHeader("Authorization") String stringToken){

        TokenModel token = new TokenModel(stringToken);
        int userId = TokenUtils.getTokenValue(customAuthenticationService,ParticipantClaims.ID.getValue(),token).asInt();
        Optional.ofNullable(getSessionSurveys(sessionId).get(surveyId)).ifPresent(survey -> {
            survey.answerSurvey(messageModel.getText(), userId);
        });


    }

    @GetMapping("admin/session/{sessionId}/survey/{surveyId}/close")
    public void close(@PathVariable int sessionId, @PathVariable int surveyId){
        //check if the survey was already published
        ArrayList<Integer> publishedSurveys = getPublishedSessionSurveys(sessionId);
        if(!publishedSurveys.contains(surveyId)){
            publishedSurveys.add(surveyId);

            SurveyEntity surveyEntity = getSessionSurveys(sessionId).get(surveyId);
            if(surveyEntity != null){
                surveyService.onClose(sessionId,surveyEntity.getId());
                surveyService.onResult(sessionId,surveyEntity);
            }
        }
    }

    //removes the need to check if sessionSurveys returns null for a given key
    private ArrayList<SurveyEntity> getSessionSurveys(int sessionId){
        return sessionSurveys.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    //removes the need to check if publishedSessionSurveys returns null for a given key
    private ArrayList<Integer> getPublishedSessionSurveys(int sessionId){
        return publishedSessionSurveys.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    private SurveyCreationModel createSurveyInSession(int sessionId, SurveyTemplateEntity surveyTemplateEntity){
        ArrayList<SurveyEntity> surveys = getSessionSurveys(sessionId);
        int newSurveyId = surveys.size();
        //create the new SurveyCreationModel
        SurveyCreationModel newSurvey = new SurveyCreationModel(surveyTemplateEntity,newSurveyId);
        //create the new SurveyEntity
        SurveyEntity surveyEntity = new SurveyEntity(newSurvey);
        //add it to the surveys in the Session with id sessionId
        surveys.add(surveyEntity);
        //start Thread to publish survey after a given amount of time
        new SurveyTimer(surveyEntity,sessionId,getPublishedSessionSurveys(sessionId),surveyService).start();

        return newSurvey;
    }
}
