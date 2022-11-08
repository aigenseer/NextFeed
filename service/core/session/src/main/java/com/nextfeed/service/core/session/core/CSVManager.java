package com.nextfeed.service.core.session.core;

import com.nextfeed.library.core.grpc.service.manager.SessionManagerServiceClient;
import com.nextfeed.library.core.proto.entity.DTOEntities;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class CSVManager {

    private final SessionManagerServiceClient sessionManagerServiceClient;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public File buildSessionZip(Integer sessionId) throws IOException {
        File tempZip = File.createTempFile("lecturefeed-tmp-session-", ".zip");
        var session = sessionManagerServiceClient.getSessionById(sessionId);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempZip));
        if(session.isPresent()){
            appendFileToZip(out, "session.csv", createSessionCSVFile(session.get()));
            appendFileToZip(out, "questions.csv", createQuestionsCSV(session.get()));
            appendFileToZip(out, "participants.csv", createParticipantCSV(session.get()));
            appendFileToZip(out, "moods.csv", createMoodCSV(session.get()));
            appendFileToZip(out, "surveys.csv", createSurveyCSV(session.get()));
        }
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

    private File createSessionCSVFile(DTOEntities.SessionDTO session) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Session Id", "Name", "Session Code", "Closed"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        printer.printRecord(session.getId(), session.getName(), session.getSessionCode(),dateFormat.format(session.getClosed()));
        out.close();
        return tempFile;
    }

    private File createQuestionsCSV(DTOEntities.SessionDTO session) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Question Id", "Message", "Rating", "Create By Participant Id", "Created", "Closed"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (DTOEntities.QuestionDTO question: session.getQuestions().getQuestionsList()) {
            printer.printRecord(question.getId(), question.getMessage(), question.getRating(), question.getParticipant().getId(), dateFormat.format(question.getCreated()), dateFormat.format(question.getClosed()));
        }
        out.close();
        return tempFile;
    }

    private File createParticipantCSV(DTOEntities.SessionDTO session) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Participant Id", "Nickname"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (DTOEntities.ParticipantDTO participant: session.getParticipants().getParticipantsList()) {
            printer.printRecord(participant.getId(), participant.getNickname());
        }
        out.close();
        return tempFile;
    }

    private File createMoodCSV(DTOEntities.SessionDTO session) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Timestamp", "Value"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (DTOEntities.MoodEntityDTO moodEntity: session.getMoodEntities().getEntriesList()) {
            printer.printRecord(dateFormat.format(moodEntity.getTimestamp()), moodEntity.getValue());
        }
        out.close();
        return tempFile;
    }

    private File createSurveyCSV(DTOEntities.SessionDTO session) throws IOException {
        File tempFile = getTempCSVFile();
        FileWriter out = new FileWriter(tempFile);
        String[] headers = { "Survey Id", "Name", "Question", "Type", "Duration", "Published", "Answers", "Created"};
        CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
        for (DTOEntities.SurveyDTO survey: session.getSurveys().getSurveysList()) {
            printer.printRecord(
                    survey.getId(),
                    survey.getTemplate().getName(),
                    survey.getTemplate().getQuestion(),
                    survey.getTemplate().getSurveyType(),
                    survey.getTemplate().getDuration(),
                    survey.getTemplate().getPublishResults(),
                    String.join(",", survey.getSurveyAnswersList().stream().map(DTOEntities.SurveyAnswerDTO::getValue).toString()),
                    dateFormat.format(survey.getTimestamp()));
        }
        out.close();
        return tempFile;
    }

}
