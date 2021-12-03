package com.lecturefeed.socket.controller.service;

import com.lecturefeed.model.MessageModel;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Deprecated
//@Data
@Service
public class AdminService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/admin/question";

    private List<Principal> principals = new ArrayList<>();

    AdminService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendQuestionToAll(String message) {
        //hier könnte man mit dem pfad des subscriber's arbeiten. z.B /admin/{principalid}/msg
        //akuell schickt es an alle die WS_MESSAGE_TRANSFER_DESTINATION subscribed haben
        simpMessagingTemplate.convertAndSend(WS_MESSAGE_TRANSFER_DESTINATION, new MessageModel(message));
    }

    public void addPrincipal(Principal principal) {
        principals.add(principal);
    }
}
