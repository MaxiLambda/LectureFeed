package com.lecturefeed.socket.controller.service;

import com.auth0.jwt.interfaces.Claim;
import com.lecturefeed.model.QuestionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

@Service
@Controller
public class QuestionService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String WS_MESSAGE_TRANSFER_DESTINATION = "/%s/session/%d/question/onupdate";
    private final String[] roots = {"admin","participant"};

    QuestionService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendQuestion(QuestionModel question, int sessionId){
                    Arrays.stream(roots).
                    map(root->WS_MESSAGE_TRANSFER_DESTINATION.formatted(root,sessionId))
                    .forEach(path->simpMessagingTemplate.convertAndSend(path,question));
    }



//    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/up")
//    public void ratingUp(@PathVariable("sessionId") Integer sessionId, @PathVariable("questionId") Integer questionId, Principal principal){
//        System.out.println("???");
//    }

    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/up")
    public void ratingUp(@DestinationVariable Integer sessionId, @DestinationVariable Integer questionId, Principal principal){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        int id = ((Map<String, Claim>) token.getCredentials()).get("id").asInt();
        System.out.println(id);
    }

//    @MessageMapping("/participant/session/{sessionId}/question/{questionId}/rating/up")
//    public void ratingUp2(@PathVariabel Principal principal){
//        System.out.println("???");
//    }
}
