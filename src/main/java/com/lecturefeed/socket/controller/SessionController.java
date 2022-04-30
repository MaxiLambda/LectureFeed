package com.lecturefeed.socket.controller;

import com.auth0.jwt.interfaces.Claim;
import com.lecturefeed.manager.MoodManager;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class SessionController {
    private final ParticipantManager participantManager;
    private final MoodManager moodManager;

    @MessageMapping("/participant/session/{sessionId}/mood/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer rating, Principal principal){
        Claim claimId = PrincipalUtils.getClaim("id", principal);
        if(claimId != null){
            int participantId = claimId.asInt();
            if(participantManager.existsParticipantId(participantId)){
               moodManager.createCalculatedMoodValue(sessionId, participantId, rating);
            }
        }
    }

}
