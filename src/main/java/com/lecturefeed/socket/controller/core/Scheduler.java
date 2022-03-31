package com.lecturefeed.socket.controller.core;

import com.lecturefeed.entity.model.MoodEntity;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.manager.MoodManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.repository.service.MoodDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.IntegerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final SessionManager sessionManager;
    private final MoodManager moodManager;

    //Todo: kann nach dem Ticket LFD-82 entfernt werden
    @Scheduled(fixedRate = 10000)
    public void schedulingTask() {
        for (Session session: sessionManager.getAllOpenSessions()) {
            moodManager.addMoodValueToSession(session.getId(), IntegerUtils.getRandomIntegerByRange(-5, 5));
        }
    }
}
