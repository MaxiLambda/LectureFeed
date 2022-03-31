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
    @OneToMany
    @JoinColumn(name = "session_id")
    private Set<Participant> participants = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Question> questions = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<Survey> surveys = Set.of();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Set<MoodEntity> moodEntities = Set.of();

    private long closed = 0;
    private String sessionCode;
    private String name;

}
