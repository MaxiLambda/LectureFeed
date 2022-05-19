package com.lecturefeed.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionModel {
    private Integer participantId;
    private Boolean anonymous;
    private String message;
    private Long created;
}
