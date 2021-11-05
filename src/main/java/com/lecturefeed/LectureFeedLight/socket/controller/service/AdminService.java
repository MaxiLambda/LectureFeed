package com.lecturefeed.LectureFeedLight.socket.controller.service;

import com.lecturefeed.LectureFeedLight.model.QuestionModel;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class AdminService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/admin/question";

    private List<Principal> principals = new ArrayList<>();

    AdminService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendQuestionToAll(String question) {
        //hier könnte man mit dem pfad des subscriber's arbeiten. z.B /admin/{principalid}/msg
        //akuell schickt es an alle die WS_MESSAGE_TRANSFER_DESTINATION subscribed haben
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION, new QuestionModel(question));
    }

    public void addPrincipal(Principal principal) {
        principals.add(principal);
    }
}
