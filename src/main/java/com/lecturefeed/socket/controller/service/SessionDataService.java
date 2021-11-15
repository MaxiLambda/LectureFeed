package com.lecturefeed.socket.controller.service;

import com.lecturefeed.session.Participant;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class SessionDataService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String WS_MESSAGE_ADMIN_TRANSFER_DESTINATION = "/admin/session/%d/user/onjoin";
    private static final String WS_MESSAGE_PARTICIPANT_TRANSFER_DESTINATION = "/participant/session/%d/user/onjoin";

    SessionDataService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendNewParticipantToAll(int sessionId, Participant participant){
        String adminPath = String.format(WS_MESSAGE_ADMIN_TRANSFER_DESTINATION,sessionId);
        String participantPath = String.format(WS_MESSAGE_PARTICIPANT_TRANSFER_DESTINATION,sessionId);

        simpMessagingTemplate.convertAndSend(adminPath, participant);
        simpMessagingTemplate.convertAndSend(participantPath,participant);
    }
}
