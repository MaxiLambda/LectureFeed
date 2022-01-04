package com.lecturefeed.socket.controller.service;

import com.lecturefeed.model.survey.SurveyEntity;
import com.lecturefeed.model.survey.SurveyCreationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String WS_MESSAGE_CREATE_TRANSFER_DESTINATION = "/session/%d/survey/oncreate";
    private static final String WS_MESSAGE_CLOSE_TRANSFER_DESTINATION = "/session/%d/survey/%d/onclose";
    private static final String WS_MESSAGE_RESULT_TRANSFER_DESTINATION = "/%s/session/%d/survey/%d/onresult";

    public void onCreate(int sessionId, SurveyCreationModel surveyCreationModel){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted(sessionId);

        simpMessagingTemplate.convertAndSend(path,surveyCreationModel);
    }

    public void onClose(int sessionId, int surveyId){
        String path = WS_MESSAGE_CLOSE_TRANSFER_DESTINATION.formatted(sessionId,surveyId);

        simpMessagingTemplate.convertAndSend(path,sessionId);
    }

    public void onResult(int sessionId, SurveyEntity surveyEntity){
        String adminPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("admin",sessionId,surveyEntity.getId());

        simpMessagingTemplate.convertAndSend(adminPath,surveyEntity);
        if(surveyEntity.isPublishResults()){
            String participantPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("participant",sessionId,surveyEntity.getId());

            simpMessagingTemplate.convertAndSend(participantPath,surveyEntity);
        }
    }
}
