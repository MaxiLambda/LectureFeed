package com.lecturefeed.model;

import lombok.Data;

@Data
public class QuestionModel {
    private Integer participantId;
    private Boolean anonymous;
    private String message;
    private Long created;
}
