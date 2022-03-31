package com.lecturefeed.socket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MoodController {

    @MessageMapping(" /participant/session/{sessionId}/mood/{participantId}/{rating}")
    public void changeMood(@DestinationVariable int sessionId,@DestinationVariable int participantId, @DestinationVariable int rating){

    }
}
