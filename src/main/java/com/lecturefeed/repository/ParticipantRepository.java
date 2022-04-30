package com.lecturefeed.repository;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {

    List<Participant> findBySession(Session session);
}
