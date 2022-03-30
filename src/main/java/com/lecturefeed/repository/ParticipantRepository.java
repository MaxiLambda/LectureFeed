package com.lecturefeed.repository;

import com.lecturefeed.entity.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
}
