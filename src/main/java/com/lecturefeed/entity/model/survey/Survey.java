package com.lecturefeed.entity.model.survey;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey")
    private Set<SurveyAnswer> surveyAnswers = new HashSet<>();

    @JsonGetter("answers")
    public List<String> getAnswers(){
        return surveyAnswers.stream().map(SurveyAnswer::getValue).toList();
    }

    @ManyToOne
    @JoinColumn(name = "template_id")
    private SurveyTemplate template;
    //the time when the survey was started
    private long timestamp;

}
