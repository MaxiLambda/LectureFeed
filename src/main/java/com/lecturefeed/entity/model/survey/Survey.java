package com.lecturefeed.entity.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    //TODO maybe replace with dedicated answer objet and @OneToMany Relations to improve Performance
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> answers = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "template_id")
    private SurveyTemplate template;
    //the time when the survey was started
    private long timestamp;
    //@Transient should prevent answeredIds from being persisted in the DB and from being returned as part of the Survey-Json Object
    @JsonIgnore
    @Transient
    @Builder.Default
    private Set<Integer> answeredIds = new HashSet<>();

    public Survey(int id, SurveyTemplate template, List<String> answers, long timestamp) {
        this.id = id;
        this.template = template;
        this.answers = answers;
        this.timestamp = timestamp;
    }

    public Survey(int id, SurveyTemplate template) {
        this.id = id;
    }

    public void addAnswer(int answeringId,String answer){
        if(!answeredIds.contains(answeringId)){
            answeredIds.add(answeringId);
            answers.add(answer);
        }
    }
}
