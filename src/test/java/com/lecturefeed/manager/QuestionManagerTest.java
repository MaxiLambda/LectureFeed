package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Question;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.repository.service.QuestionDBService;
import com.lecturefeed.socket.controller.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionManagerTest {

    QuestionManager questionManager;

    @Mock
    QuestionDBService questionDBService;
    @Mock
    SessionManager sessionManager;
    @Mock
    QuestionService questionService;
    @Mock
    ParticipantDBService participantDBService;

    int sessionId = 1;
    int participantId = 2;
    int questionId = 3;
    String message = "test";
    long createdAt = 2L;
    boolean anonymous = false;
    Session session;
    Participant participant;
    QuestionModel questionModel;
    Question question;

    @BeforeEach
    void setUp() {

        participant = Participant.builder()
                .id(participantId)
                .build();

        HashSet<Participant> participants = new HashSet<>();
        participants.add(participant);
        session = Session.builder()
                .id(sessionId)
                .participants(participants)
                .build();

        questionModel = QuestionModel.builder()
                .participantId(participantId)
                .message(message)
                .created(createdAt)
                .anonymous(anonymous)
                .build();

        question = Question.builder()
                .participant(participant)
                .message(message)
                .rating(0)
                .created(createdAt)
                .anonymous(anonymous)
                .build();
        questionManager = new QuestionManager(questionDBService,sessionManager,questionService,participantDBService);
    }

    @Test
    void createQuestionByModel() {


        when(participantDBService.findById(participantId)).thenReturn(participant);
        when(sessionManager.getSessionById(sessionId)).thenReturn(session);

        assertEquals(question,questionManager.createQuestionByModel(sessionId,questionModel));

        verify(questionService,times(1)).sendQuestion(sessionId,question);
    }

    @Test
    void ratingUpByQuestionId() {
        boolean rating = true;

        when(questionDBService.findById(questionId)).thenReturn(question);
        when(participantDBService.findById(participantId)).thenReturn(participant);
        when(sessionManager.getSessionById(sessionId)).thenReturn(session);

        assertEquals(0,session.getQuestions().size());
        assertEquals(0,question.getVoters().size());
        questionManager.ratingUpByQuestionId(sessionId,questionId,participantId,rating);
        assertEquals(1,session.getQuestions().size());
        assertEquals(1,question.getVoters().size());
        assertEquals(1,question.getRating());

        //verify that you can't vote twice
        questionManager.ratingUpByQuestionId(sessionId,questionId,participantId,rating);
        assertEquals(1,session.getQuestions().size());
        assertEquals(1,question.getVoters().size());
        assertEquals(1,question.getRating());

        verify(questionService,times(1)).sendQuestion(sessionId,question);

    }

    @Test
    void closeQuestion() {
        when(questionDBService.findById(questionId)).thenReturn(question);
        when(sessionManager.getSessionById(sessionId)).thenReturn(session);

        assertNull(question.getClosed());
        questionManager.closeQuestion(sessionId,questionId);
        assertNotNull(question.getClosed());
        long closed = question.getClosed();

        questionManager.closeQuestion(sessionId,questionId);
        assertEquals(closed,question.getClosed());


        verify(questionService,times(1)).sendQuestion(sessionId,question);
    }
}