package com.lecturefeed.entity.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nickname;
    @Transient
    private boolean connected = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="session_id", nullable=false)
    private Session session;

}
