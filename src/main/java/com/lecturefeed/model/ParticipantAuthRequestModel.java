package com.lecturefeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantAuthRequestModel {
    private int sessionId;
    private String nickname;
    private String sessionCode;
}
