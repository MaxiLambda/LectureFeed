package com.lecturefeed.model;

import com.lecturefeed.entity.model.Participant;
import lombok.Data;

@Data
public class QuestionModel {
    private Integer participantId;
    private String message;
    private Integer rating = 0;
    private Long created;
}
