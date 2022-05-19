package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Question;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.manager.command.QuestionRatingChanger;
import com.lecturefeed.manager.command.RatingChange;
import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.repository.service.QuestionDBService;
import com.lecturefeed.socket.controller.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionManager implements QuestionRatingChanger {

    private final QuestionDBService questionDBService;
    private final SessionManager sessionManager;
    private final QuestionService questionService;
    private final ParticipantDBService participantService;


    public boolean existsQuestionId(int questionId){
        return questionDBService.findById(questionId) != null;
    }

    public Question createQuestionByModel(int sessionId, QuestionModel model){
        Participant participant = participantService.findById(model.getParticipantId());
        Question question = Question.builder()
                .participant(participant)
                .message(model.getMessage())
                .rating(0)
                .created(model.getCreated())
                .anonymous(model.getAnonymous())
                .build();
        questionDBService.save(question);
        Session session = sessionManager.getSessionById(sessionId);
        session.getQuestions().add(question);
        sessionManager.saveSession(session);
        questionService.sendQuestion(sessionId, question);
        return question;
    }

    public void changeRating(RatingChange ratingChange){
        Question question = questionDBService.findById(ratingChange.getQuestionId());
        Participant voter = participantService.findById(ratingChange.getVoterId());

        if (!question.getVoters().contains(voter)){
            question.getVoters().add(voter);
            question.setRating(question.getRating() + (ratingChange.isRatingChange()? +1: -1));
            questionDBService.save(question);
            updateQuestion(ratingChange.getSessionId(), question);
            questionService.sendQuestion(ratingChange.getSessionId(), question);
        }
    }

    public void closeQuestion(int sessionId, int questionId){
        Question question = questionDBService.findById(questionId);
        if(question.getClosed() == null){
            question.setClosed(System.currentTimeMillis());
            questionDBService.save(question);
            updateQuestion(sessionId, question);
            questionService.sendQuestion(sessionId, question);
        }
    }

    private void updateQuestion(int sessionId, Question question){
        sessionManager.getSessionById(sessionId).getQuestions().add(question);
    }

}
