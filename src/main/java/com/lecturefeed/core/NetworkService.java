package com.lecturefeed.core;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Optional;

@AllArgsConstructor
@Component
public class NetworkService {

    public Optional<InetAddress> getRoutingInterface(){
        return OSFactory.getOSSystem().getRoutingInterface();
    }

}
