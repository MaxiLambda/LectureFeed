package com.lecturefeed.socket.controller.service;

import com.lecturefeed.model.QuestionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String WS_MESSAGE_TRANSFER_DESTINATION = "/%s/session/%d/question/onupdate";
    private final String[] roots = {"admin","participant"};

    public void sendQuestion(QuestionModel question, int sessionId){
        Arrays.stream(roots).
                map(root->WS_MESSAGE_TRANSFER_DESTINATION.formatted(root,sessionId))
                .forEach(path->simpMessagingTemplate.convertAndSend(path,question));
    }


    @MessageMapping("/ab/a")
    public void ratingUp(QuestionModel question, Principal principal){

    }
}
