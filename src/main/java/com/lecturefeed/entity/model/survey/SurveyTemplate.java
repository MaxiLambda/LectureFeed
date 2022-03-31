package com.lecturefeed.entity.model.survey;

import com.lecturefeed.model.survey.SurveyType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SurveyTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private SurveyType type;
    private String question;
    //always >0
    private long duration;
    private boolean publishResults;
}
