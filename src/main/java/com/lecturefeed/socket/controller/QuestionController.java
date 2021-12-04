package com.lecturefeed.socket.controller;


import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.session.NoSessionFoundException;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.NoPermissionException;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final SessionManager sessionManager;
    private final QuestionService questionService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    //%d is the sessionId, %s has to be the username to mimic @SendToUser
    private final String WS_MESSAGE_TRANSFER_DESTINATION = "/participant/session/%d/question/oncreate/user/%s";

    //SimpMessageHeaderAccessor contains header Information for the send message
    @PostMapping("/participant/session/{sessionId}/question/create")
    public int createQuestion(QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){

        sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel.getId();

    }
}
