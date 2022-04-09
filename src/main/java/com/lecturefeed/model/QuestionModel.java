package com.lecturefeed.model;

import lombok.Data;

@Data
public class QuestionModel {
    private Integer participantId;
    private String message;
    private Integer rating = 0;
    private Long created;
}
