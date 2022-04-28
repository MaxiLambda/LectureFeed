package com.lecturefeed.entity.model;

import com.lecturefeed.entity.model.survey.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "session")
    private Set<Participant> participants = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Question> questions = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Survey> surveys = new HashSet<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<MoodEntity> moodEntities = new HashSet<>();

    private long closed = 0;
    private String sessionCode;
    private String name;

}
