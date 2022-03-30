package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.QuestionModel;
import com.lecturefeed.repository.QuestionModelRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionModelDBService extends AbstractService<QuestionModel, QuestionModelRepository> {
    public QuestionModelDBService(QuestionModelRepository questionModelRepository) {
        super(questionModelRepository);
    }
}
