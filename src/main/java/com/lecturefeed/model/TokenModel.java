package com.lecturefeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class TokenModel {
    private final String token;
    private final int userId;
}
