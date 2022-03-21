package com.lecturefeed.restapi.controller;

import com.lecturefeed.model.MessageModel;
import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.model.survey.SurveyType;
import com.lecturefeed.socket.controller.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class SurveyRestController {

    private final SurveyService surveyService;

    private final HashMap<Integer, SurveyTemplate> templates = new HashMap<>();
    private final HashMap<Integer, ArrayList<Survey>> sessionSurveys = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> publishedSessionSurveys = new HashMap<>();

    public SurveyRestController(SurveyService surveyService){
        this.surveyService = surveyService;
        //test
        SurveyTemplate template = new SurveyTemplate();
        template.setId(1);
        template.setName("TestTemplate");
        template.setQuestion("Question?");
        template.setDuration(5);
        template.setType(SurveyType.OpenAnswer);
        template.setPublishResults(true);
        templates.put(1, template);
        List<String> answers = new ArrayList<>();
        answers.add("answer1");
        answers.add("answer2");
        Survey survey = new Survey(1, template, answers, new Date().getTime());
        getSessionSurveyList(1).add(survey);
    }


    @GetMapping("/admin/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId){
        //todo Abfrage sessionId exists und alle templates zur sessionId
        return templates.values();
    }

    @GetMapping("/admin/session/{sessionId}/surveys")
    public ArrayList<Survey> getSessionSurveys(@PathVariable int sessionId){
        //todo Abfrage sessionId exists
        List<Survey> surveys = getSessionSurveyList(sessionId);
        if(surveys == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Surveys not exists", surveys));
        }
        return getSessionSurveyList(sessionId);
    }

    @GetMapping("/admin/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        //todo Abfrage sessionId exists
        SurveyTemplate template = getTemplateById(templateId);
        if(template != null){
            createSurveyInSession(sessionId, template);
            surveyService.onCreate(sessionId, template);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SurveyTemplate-ID %d not exists", templateId));
        }
    }

    @PostMapping("/admin/session/{sessionId}/survey/create")
    public void create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        createSurveyInSession(sessionId, template);
        surveyService.onCreate(sessionId, template);
    }

    //TODO PROBLEM: users (also random people) can use the api to submit multiple responses
    @PostMapping("/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageModel messageModel){
        Optional.ofNullable(getSessionSurveyList(sessionId).get(surveyId)).
                map(Survey::getAnswers).
                map(answers -> answers.add(messageModel.getText()));
    }

    @GetMapping("/admin/session/{sessionId}/survey/{surveyId}/close")
    public void close(@PathVariable int sessionId, @PathVariable int surveyId){
        //check if the survey was already published
        ArrayList<Integer> publishedSurveys = getPublishedSessionSurveys(sessionId);
        if(!publishedSurveys.contains(surveyId)){
            publishedSurveys.add(surveyId);

            Survey survey = getSessionSurveyList(sessionId).get(surveyId);
            if(survey != null){
                surveyService.onClose(sessionId, survey.getId());
                surveyService.onResult(sessionId, survey);
            }
        }
    }

    private SurveyTemplate getTemplateById(int templateId){
        return templates.get(templateId);
    }

    //removes the need to check if sessionSurveys returns null for a given key
    private ArrayList<Survey> getSessionSurveyList(int sessionId){
        return sessionSurveys.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    //removes the need to check if publishedSessionSurveys returns null for a given key
    private ArrayList<Integer> getPublishedSessionSurveys(int sessionId){
        return publishedSessionSurveys.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }

    private SurveyTemplate createSurveyInSession(int sessionId, SurveyTemplate template){
        //create the new SurveyCreationModel
        template.setId(templates.size()+1);
        templates.put(templates.size(), template);

        ArrayList<Survey> sessionSurveys = getSessionSurveyList(sessionId);
        //create the new SurveyEntity
        Survey survey = new Survey(sessionSurveys.size()+1, template, new ArrayList<>(),System.currentTimeMillis());
        //add it to the surveys in the Session with id sessionId
        sessionSurveys.add(sessionId, survey);
        //start Thread to publish survey after a given amount of time
        new SurveyTimer(survey,sessionId,getPublishedSessionSurveys(sessionId),surveyService).start();

        return template;
    }
}
