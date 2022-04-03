package com.lecturefeed.manager;

import com.lecturefeed.entity.model.MoodEntity;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Question;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.survey.Survey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class CSVManager {

    private final SessionManager sessionManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public File buildSessionZip(Integer sessionId) throws IOException {
        File tempZip = File.createTempFile("lecturefeed-tmp-session-", ".zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempZip));
        appendFileToZip(out, "session.csv", createSessionCSVFile(sessionId));
        appendFileToZip(out, "questions.csv", createQuestionsCSV(sessionId));
        appendFileToZip(out, "participants.csv", createParticipantCSV(sessionId));
        appendFileToZip(out, "moods.csv", createMoodCSV(sessionId));
        appendFileToZip(out, "surveys.csv", createSurveyCSV(sessionId));
        out.close();
        return tempZip;
    }

    private void appendFileToZip(ZipOutputStream out, String filename, File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        out.putNextEntry(new ZipEntry(filename));
        byte[] b = new byte[1024];
        int count;
        while ((count = in.read(b)) > 0) {
            out.write(b, 0, count);
        }
        in.close();
    }

    private File getTempCSVFile() throws IOException {
        return File.createTempFile("lecturefeed-tmp-csv-", ".csv");
    }

    private File createSessionCSVFile(Integer sessionId) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Session Id", "Name", "Session Code", "Closed"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        Session session = sessionManager.getSessionById(sessionId);
        printer.printRecord(session.getId(), session.getName(), session.getSessionCode(),dateFormat.format(session.getClosed()));
        out.close();
        return tempFile;
    }

    private File createQuestionsCSV(Integer sessionId) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Question Id", "Message", "Rating", "Create By Participant Id", "Created", "Closed"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (Question question: sessionManager.getSessionById(sessionId).getQuestions()) {
            printer.printRecord(question.getId(), question.getMessage(), question.getRating(), question.getParticipant().getId(), dateFormat.format(question.getCreated()), dateFormat.format(question.getClosed()));
        }
        out.close();
        return tempFile;
    }

    private File createParticipantCSV(Integer sessionId) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Participant Id", "Nickname"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (Participant participant: sessionManager.getSessionById(sessionId).getParticipants()) {
            printer.printRecord(participant.getId(), participant.getNickname());
        }
        out.close();
        return tempFile;
    }

    private File createMoodCSV(Integer sessionId) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Timestamp", "Value"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (MoodEntity moodEntity: sessionManager.getSessionById(sessionId).getMoodEntities()) {
            printer.printRecord(dateFormat.format(moodEntity.getTimestamp()), moodEntity.getValue());
        }
        out.close();
        return tempFile;
    }

    private File createSurveyCSV(Integer sessionId) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Survey Id", "Name", "Question", "Type", "Duration", "Published", "Answers", "Created"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (Survey survey: sessionManager.getSessionById(sessionId).getSurveys()) {
            printer.printRecord(
                    survey.getId(),
                    survey.getTemplate().getName(),
                    survey.getTemplate().getQuestion(),
                    survey.getTemplate().getType().toString(),
                    survey.getTemplate().getDuration(),
                    survey.getTemplate().isPublishResults(),
                    String.join(",", survey.getAnswers()),
                    dateFormat.format(survey.getTimestamp()));
        }
        out.close();
        return tempFile;
    }

}
