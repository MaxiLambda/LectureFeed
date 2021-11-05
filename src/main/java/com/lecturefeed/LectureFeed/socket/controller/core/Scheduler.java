package com.lecturefeed.LectureFeed.socket.controller.core;

import com.lecturefeed.LectureFeed.socket.controller.service.AdminService;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final AdminService adminService;

    Scheduler(AdminService greetingService) {
        this.adminService = greetingService;
    }

//    @Scheduled(fixedRateString = "6000", initialDelayString = "0")
//    public void schedulingTask() {
//        //adminService.sendMessages();
//    }
}
