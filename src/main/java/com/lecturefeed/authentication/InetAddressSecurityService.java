package com.lecturefeed.authentication;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class InetAddressSecurityService {

    private final List<InetAddress> blockedIpAddresses = new ArrayList<>();

    public void blockInetAddress(InetAddress inetAddress){
        if(inetAddress != null){
            blockedIpAddresses.add(inetAddress);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("???");
                    blockedIpAddresses.remove(inetAddress);
                }
            }, 3*1000);
        }
    }

    public boolean isInetAddressBlocked(InetAddress inetAddress){
        return blockedIpAddresses.contains(inetAddress);
    }

}
