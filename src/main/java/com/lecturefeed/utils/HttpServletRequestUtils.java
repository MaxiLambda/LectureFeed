package com.lecturefeed.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpServletRequestUtils {

    private HttpServletRequestUtils() {
    }

    public static InetAddress getRemoteAddrByRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            try {
                return InetAddress.getByName(request.getRemoteAddr());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
