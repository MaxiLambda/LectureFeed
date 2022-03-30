package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.QuestionModel;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.repository.service.QuestionModelDBService;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final QuestionModelDBService questionModelDBService;
    private final SessionManager sessionManager;
    private final QuestionService questionService;
    private final ParticipantDBService participantService;


    public boolean existsQuestionId(int sessionId, int questionId){
        return questionModelDBService.findById(questionId) == null;
    }

    public QuestionModel createQuestion(int sessionId, QuestionModel question){
        Session session = sessionManager.getSessionById(sessionId);
        session.getQuestions().add(question);
        sessionManager.saveSession(session);
        questionService.sendQuestion(sessionId, question);
        return question;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        QuestionModel questionModel = questionModelDBService.findById(questionId);
        Participant voter = participantService.findById(voterId);

        if (!questionModel.getVoters().contains(voter)){
            questionModel.getVoters().add(voter);
            questionModel.setRating(questionModel.getRating() + (rating? +1: -1));
            updateQuestion(sessionId, questionModel);
            questionModelDBService.save(questionModel);
            questionService.sendQuestion(sessionId, questionModel);
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        QuestionModel questionModel = questionModelDBService.findById(questionId);
        if(questionModel.getClosed() == null){
            questionModel.setClosed(System.currentTimeMillis());
            questionModelDBService.save(questionModel);
            updateQuestion(sessionId, questionModel);
            questionService.sendQuestion(sessionId, questionModel);
        }
    }

    private void updateQuestion(int sessionId, QuestionModel questionModel){
        sessionManager.getSessionById(sessionId).getQuestions().add(questionModel);
    }

}
