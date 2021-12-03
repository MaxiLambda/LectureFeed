package com.lecturefeed.socket.controller;


import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

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
    @MessageMapping("/participant/session/{sessionId}/question/create")
    public void createQuestion(QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId, SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        try{
            sessionManager.getSession(sessionId).orElseThrow().addQuestion(questionModel);
        }catch (NoSuchElementException e){
            System.out.println("ERROR: Question send from invalid session!");
            return;
        }
        String username = Objects.requireNonNull(simpMessageHeaderAccessor.getUser()).getName();

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);
        //mimic @SendToUser because it can't use variable paths
        simpMessagingTemplate.
                convertAndSend(
                        WS_MESSAGE_TRANSFER_DESTINATION.formatted(sessionId,username),
                        questionModel.getId()
                );
    }
}
