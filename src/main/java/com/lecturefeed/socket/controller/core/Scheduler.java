package com.lecturefeed.socket.controller.core;

import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.IntegerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final SessionManager sessionManager;
    private final SessionDataService sessionDataService;

    //Todo: kann nach dem Ticket LFD-82 entfernt werden
    @Scheduled(fixedRate = 10000)
    public void schedulingTask() {
        for (Integer id: sessionManager.getAllSessionIds()) {
            //sessionDataService.sendMood(id, IntegerUtils.getRandomIntegerByRange(-5, 5));
        }
    }
}
