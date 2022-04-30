package com.lecturefeed.authentication;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@AllArgsConstructor
@Service
public class InetAddressSecurityService {

    private final List<InetAddress> blockedIpAddresses = new ArrayList<>();
    private final Environment env;

    public void blockInetAddress(InetAddress inetAddress){
        if(inetAddress != null){
            blockedIpAddresses.add(inetAddress);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    blockedIpAddresses.remove(inetAddress);
                }
            }, (long) getBanTimeMin() *60*1000);
        }
    }

    private int getBanTimeMin(){
        String prop = env.getProperty("lecturefeed.security.participant.ban.min");
        if(prop != null){
            return Integer.parseInt(prop);
        }
        return 10;
    }

    public boolean isInetAddressBlocked(InetAddress inetAddress){
        return blockedIpAddresses.contains(inetAddress);
    }

}
