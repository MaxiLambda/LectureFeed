package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.manager.QuestionManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.model.*;
import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyType;
import com.lecturefeed.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/session")
public class SessionRestController {

    private final SessionManager sessionManager;
    private final QuestionManager questionManager;
    private final TokenService tokenService;

    @PostMapping("/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody CreateSessionModel createSessionModel) {
        Session session = sessionManager.createSession(createSessionModel.getName());
        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id",session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);
        //todo: check session active
        sessionManager.closeSession(sessionId);
    }

    @GetMapping("/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        if(!SecurityContextHolderUtils.isCurrentUserAdmin()) tokenService.checkSessionIdByToken(token, sessionId);
        sessionManager.checkSessionId(sessionId);

        Session session = sessionManager.getSessionById(sessionId);
        List<Participant> participants = session.getParticipants();
        Map<Integer, QuestionModel> questions = session.getQuestions();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions.values());
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        //todo: check session active and delete session
        sessionManager.checkSessionId(sessionId);
        //Session session = sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new);
    }

    @PostMapping("/{sessionId}/question/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){
        sessionManager.checkSessionId(sessionId);
        return questionManager.createQuestion(sessionId, questionModel);
    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {

        List<SessionMetadata> dummyValues = new ArrayList<>();
        SessionMetadata dummyValue1 = new SessionMetadata();
        dummyValue1.setSessionId(99);
        dummyValue1.setName("DummyName1");
        dummyValue1.setClosed(new Date().getTime());
        dummyValues.add(dummyValue1);
        SessionMetadata dummyValue2 = new SessionMetadata();
        dummyValue2.setSessionId(98);
        dummyValue2.setName("DummyName2");
        dummyValue2.setClosed(new Date().getTime());
        dummyValues.add(dummyValue2);
        return dummyValues;
    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/{sessionId}/data")
    public Session getSessionData(@PathVariable("sessionId") Integer sessionId) {
        //sessionManager.checkSessionId(sessionId);


        SurveyTemplate template = new SurveyTemplate(1, "templateName", SurveyType.YesNo, "question?", 5, true);
        List<String> answers = new ArrayList<>();
        answers.add("-1");
        answers.add("1");
        answers.add("1");
        HashMap<Integer, Survey> surveys = new HashMap<>();
        surveys.put(1, new Survey(1, template, answers, new Date().getTime()));


        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(1, "Participant1"));
        participants.add(new Participant(2, "Participant2"));
        HashMap<Integer, QuestionModel> questions = new HashMap<>();

        HashSet<Integer> voters = new HashSet<>();
        voters.add(1); voters.add(2);
        questions.put(1, new QuestionModel(1, 1, "Question1?", -5, new Date().getTime(), new Date().getTime(), voters));
        questions.put(2, new QuestionModel(2, 2, "Question2?", 3, new Date().getTime(), new Date().getTime(), voters));

        return new Session(99, "DummyName1", "CODE", participants, questions, surveys);
    }

    //TODO: LFD-92 //Aktuell Testdaten
    @GetMapping("/presenter/{sessionId}/data/download")
    public FileSystemResource downloadSessionData(@PathVariable("sessionId") Integer sessionId) {
        //sessionManager.checkSessionId(sessionId);
        try {
            File tempFile = File.createTempFile("prefix-", "-suffix");
            String s = System.lineSeparator() + "New Line!";
            BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(tempFile.getPath()), StandardOpenOption.APPEND));
            out.write(s.getBytes());
            out.close();

            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Failed to create data by session id %d", sessionId));
        }
    }

}
