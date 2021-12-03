package com.lecturefeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class QuestionModel {
    //null means the question is not yet handled by the server
    private Integer id = null;
    //null means the question was send anonymous
    private final Integer participantId;
    private final String message;
    private Integer rating = 0;
    private final Long created;
    //null means the question was never closed
    private Long closed;
}
