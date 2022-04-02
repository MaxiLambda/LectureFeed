package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantDBService extends AbstractService<Participant, ParticipantRepository> {
    public ParticipantDBService(ParticipantRepository participantRepository) {
        super(participantRepository);
    }


    public List<Participant> findBySession(Session s){
        return repo.findBySession(s);
    }
}
