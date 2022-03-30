package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.manager.SurveyManager;
import com.lecturefeed.manager.SurveyTemplateManager;
import com.lecturefeed.model.MessageModel;
import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SurveyRestController {

    private final SurveyManager surveyManager;
    private final SurveyTemplateManager surveyTemplateManager;
    private final SessionManager sessionManager;
    private final TokenService tokenService;


    @GetMapping("/admin/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId){
        sessionManager.checkSessionId(sessionId);
        return surveyTemplateManager.getAllTemplates();
    }

    @GetMapping("/admin/session/{sessionId}/surveys")
    public Collection<Survey> getSessionSurveys(@PathVariable int sessionId){
        sessionManager.checkSessionId(sessionId);
        return surveyManager.getSurveysBySessionId(sessionId);
    }

    @GetMapping("/admin/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        sessionManager.checkSessionId(sessionId);
        surveyTemplateManager.checkTemplateId(templateId);
        surveyManager.createSurvey(sessionId, surveyTemplateManager.getTemplateById(templateId));
    }

    @PostMapping("/admin/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        sessionManager.checkSessionId(sessionId);
        surveyManager.createSurvey(sessionId, surveyTemplateManager.createTemplate(template));
        return template;
    }

    @PostMapping("/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageModel messageModel, @RequestHeader("Authorization") String token){
        tokenService.checkSessionIdByToken(token, sessionId);
        sessionManager.checkSessionId(sessionId);
        SecurityContextHolderUtils.getCurrentAuthenticationId().ifPresent(id ->
                surveyManager.addAnswerToSurvey(sessionId, surveyId,id, messageModel.getText())
        );

    }

}
