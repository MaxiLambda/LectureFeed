package com.lecturefeed.socket.controller;

import com.lecturefeed.session.Session;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.QuestionService;
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
    private final QuestionService questionService;

    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, @DestinationVariable String rating, Principal principal){
        int userId = PrincipalUtils.getClaim("id",principal).asInt();

        sessionManager.
                getSession(sessionId).
                map(Session::getQuestions).
                //questionId -1 because the questionId is the position
                // of the question in the questions ArrayList
                map(questions-> questions.get(questionId -1)).
                ifPresent(question ->{
                    switch (rating){
                        case "up" -> question.ratingUp(userId);
                        case "down" -> question.ratingDown(userId);
                    }
                    questionService.sendQuestion(question,sessionId);
                });
    }

    @MessageMapping("/admin/session/{sessionId}/question/{questionId}/closed")
    public void closeQuestion(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId){
        sessionManager.
                getSession(sessionId).
                map(Session::getQuestions).
                //questionId -1 because the questionId is the position
                // of the question in the questions ArrayList
                        map(questions-> questions.get(questionId -1)).
                ifPresent(question ->{
                    question.closeQuestion();
                    questionService.sendQuestion(question,sessionId);
                });
    }
}
