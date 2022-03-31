package com.lecturefeed.manager;

import com.lecturefeed.entity.model.MoodEntity;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.service.MoodDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class MoodManager {
    private final MoodDBService moodDBService;
    private final SessionManager sessionManager;
    private final SessionDataService sessionDataService;

    public MoodEntity addMoodValueToSession(int sessionId, int moodValue){
        MoodEntity moodEntity = MoodEntity.builder().value(moodValue).timestamp(new Date().getTime()).build();
        Session session = sessionManager.getSessionById(sessionId);
        moodDBService.save(moodEntity);
        session.getMoodEntities().add(moodEntity);
        sessionManager.saveSession(session);
        sessionDataService.sendMood(sessionId, moodEntity.getValue());
        return moodEntity;
    }

}
