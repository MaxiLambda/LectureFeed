package com.lecturefeed.LectureFeedLight.socket.controller;


import com.lecturefeed.LectureFeedLight.socket.controller.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;

@AllArgsConstructor
@Controller
public class AdminController {

    @Getter
    private final AdminService adminService;

    @MessageMapping("/admin/question/add")
    public void msg(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        adminService.sendQuestionToAll(message);
        //return HtmlUtils.htmlEscape(message);
    }

}
