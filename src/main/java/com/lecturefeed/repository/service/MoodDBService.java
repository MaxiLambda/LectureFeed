package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.MoodEntity;
import com.lecturefeed.repository.MoodRepository;
import org.springframework.stereotype.Service;

@Service
public class MoodDBService extends AbstractService<MoodEntity, MoodRepository> {

    public MoodDBService(MoodRepository moodRepository) {
        super(moodRepository);
    }
}
