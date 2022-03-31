package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantDBService extends AbstractService<Participant, ParticipantRepository> {
    public ParticipantDBService(ParticipantRepository participantRepository) {
        super(participantRepository);
    }
}
