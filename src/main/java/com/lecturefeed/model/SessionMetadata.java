package com.lecturefeed.model;

import lombok.Data;

@Data
public class SessionMetadata {
    private int sessionId;
    private String name;
    private long closed;
}
