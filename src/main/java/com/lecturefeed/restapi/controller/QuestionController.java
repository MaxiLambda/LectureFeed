package com.lecturefeed.restapi.controller;


import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.session.NoSessionFoundException;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final SessionManager sessionManager;
    private final QuestionService questionService;

    @PostMapping("/participant/session/{sessionId}/question/create")
    public int createQuestion(QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){

        sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel.getId();

    }
}
