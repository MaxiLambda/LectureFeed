package com.lecturefeed.socket.controller;


import com.lecturefeed.socket.controller.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
@Deprecated
public class AdminController {

    @Getter
    private final AdminService adminService;

    @MessageMapping("/admin/question/add")
    public void msg(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        adminService.sendQuestionToAll(message);
    }

}
