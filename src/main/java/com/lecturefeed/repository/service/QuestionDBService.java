package com.lecturefeed.repository.service;

import com.lecturefeed.entity.model.Question;
import com.lecturefeed.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionDBService extends AbstractService<Question, QuestionRepository> {
    public QuestionDBService(QuestionRepository questionRepository) {
        super(questionRepository);
    }
}
