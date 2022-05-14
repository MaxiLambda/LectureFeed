package com.lecturefeed.restapi.controller;


import com.lecturefeed.core.NetworkService;
import com.lecturefeed.model.EnvironmentInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/environment")
public class EnvironmentRestController {

    private final NetworkService networkService;

    @GetMapping("/info")
    public EnvironmentInfo getEnvironmentInfo() {
        try {
            Optional<InetAddress> inetAddress =  networkService.getRoutingInterface();
            String ip = null;
            if(inetAddress.isPresent()){
                ip = inetAddress.get().getHostAddress();
            }
            else
            {
                ip = "127.0.0.1";
            }
            return new EnvironmentInfo(ip);
        }
        catch (Exception e)
        {
            //needed for tests to run on docker
            return new EnvironmentInfo("127.0.0.1");
        }

    }
}
