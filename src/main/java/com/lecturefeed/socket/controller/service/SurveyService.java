package com.lecturefeed.socket.controller.service;

import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyParticipantData;
import com.lecturefeed.entity.model.survey.SurveyTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String WS_MESSAGE_CREATE_TRANSFER_DESTINATION = "/%s/session/%d/survey/oncreate";
    private static final String WS_MESSAGE_CLOSE_TRANSFER_DESTINATION = "/session/%d/survey/%d/onclose";
    private static final String WS_MESSAGE_RESULT_TRANSFER_DESTINATION = "/%s/session/%d/survey/%d/onresult";
    private static final String WS_MESSAGE_UPDATE_TRANSFER_DESTINATION = "/admin/session/%d/survey/%d/onupdate";

    public void onCreateByAdmin(int sessionId, Survey survey){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("admin", sessionId);
        simpMessagingTemplate.convertAndSend(path, survey);
    }

    public void onCreateByParticipant(int sessionId, int surveyId, SurveyTemplate template){
        String path = WS_MESSAGE_CREATE_TRANSFER_DESTINATION.formatted("participant", sessionId);
        simpMessagingTemplate.convertAndSend(path, new SurveyParticipantData(surveyId, template));
    }

    public void onClose(int sessionId, int surveyId){
        String path = WS_MESSAGE_CLOSE_TRANSFER_DESTINATION.formatted(sessionId,surveyId);

        simpMessagingTemplate.convertAndSend(path,sessionId);
    }

    public void onUpdate(int sessionId, Survey survey){
        String path = WS_MESSAGE_UPDATE_TRANSFER_DESTINATION.formatted(sessionId, survey.getId());
        simpMessagingTemplate.convertAndSend(path, survey);
    }

    public void onResult(int sessionId, Survey survey){
        String adminPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("admin",sessionId, survey.getId());

        simpMessagingTemplate.convertAndSend(adminPath, survey);
        if(survey.getTemplate().isPublishResults()){
            String participantPath = WS_MESSAGE_RESULT_TRANSFER_DESTINATION.formatted("participant",sessionId, survey.getId());
            simpMessagingTemplate.convertAndSend(participantPath, survey);
        }
    }
}
