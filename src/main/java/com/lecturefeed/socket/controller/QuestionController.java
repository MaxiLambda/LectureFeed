package com.lecturefeed.socket.controller;

import com.lecturefeed.manager.QuestionManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final SessionManager sessionManager;
    private final QuestionManager questionManager;


    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, @DestinationVariable String rating, Principal principal){
        int userId = PrincipalUtils.getClaim("id", principal).asInt();
        if(sessionManager.existsSessionId(sessionId) && questionManager.existsQuestionId(sessionId, questionId)){
            boolean r = false;
            switch (rating){
                case "up" -> r = true;
                case "down" -> r = false;
            }
            questionManager.ratingUpByQuestionId(sessionId, questionId, userId, r);
        }
    }

    @MessageMapping("/admin/session/{sessionId}/question/{questionId}/close")
    public void closeQuestion(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId){
        if(sessionManager.existsSessionId(sessionId) && questionManager.existsQuestionId(sessionId, questionId)){
            questionManager.closeQuestion(sessionId, questionId);
        }
    }
}
