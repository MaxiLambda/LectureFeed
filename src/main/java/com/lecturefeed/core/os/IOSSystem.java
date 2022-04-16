package com.lecturefeed.core.os;

import java.net.InetAddress;
import java.util.Optional;

public interface IOSSystem {

    Optional<InetAddress> getRoutingInterface();

}
