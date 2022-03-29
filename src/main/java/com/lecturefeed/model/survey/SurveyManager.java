package com.lecturefeed.model.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Getter
public class SurveyManager {
    private final HashMap<Integer, SurveyTemplate> templates = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, Survey>> sessionSurveys = new HashMap<>();




}
