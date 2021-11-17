package com.lecturefeed.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ParticipantAuthRequestModel {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
