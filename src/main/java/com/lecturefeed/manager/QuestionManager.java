package com.lecturefeed.manager;

import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final SessionManager sessionManager;
    private final QuestionService questionService;

    public boolean existsQuestionId(int sessionId, int questionId){
        return sessionManager.getSessionById(sessionId).getQuestions().containsKey(questionId);
    }

    public QuestionModel createQuestion(int sessionId, QuestionModel question){
        int id = sessionManager.getSessionById(sessionId).getQuestions().size();
        question.setId(id);
        sessionManager.getSessionById(sessionId).getQuestions().put(id, question);
        questionService.sendQuestion(sessionId, question);
        return question;
    }

    public void ratingUpByQuestionId(int sessionId, int questionId, int voterId, boolean rating){
        QuestionModel questionModel = sessionManager.getSessionById(sessionId).getQuestions().get(questionId);
        if (!questionModel.getVoters().contains(voterId)){
            questionModel.getVoters().add(voterId);
            questionModel.setRating(questionModel.getRating() + (rating? +1: -1));
            updateQuestion(sessionId, questionModel);
            questionService.sendQuestion(sessionId, questionModel);
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        QuestionModel questionModel = sessionManager.getSessionById(sessionId).getQuestions().get(questionId);
        if(questionModel.getClosed() == null){
            questionModel.setClosed(System.currentTimeMillis());
            updateQuestion(sessionId, questionModel);
            questionService.sendQuestion(sessionId, questionModel);
        }
    }

    private void updateQuestion(int sessionId, QuestionModel questionModel){
        sessionManager.getSessionById(sessionId).getQuestions().put(questionModel.getId(), questionModel);
    }

}
