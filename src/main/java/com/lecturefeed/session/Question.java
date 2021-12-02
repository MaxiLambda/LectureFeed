package com.lecturefeed.session;

import lombok.Data;

import java.security.Timestamp;

@Data
public class Question {
    private final int id;
    private final int participantId;
    private final String message;
    private int rating;
    private Timestamp created;
    private Timestamp closed;
}
