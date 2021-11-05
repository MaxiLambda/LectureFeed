package com.lecturefeed.LectureFeed.socket.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
public class StompPrincipal implements Principal {
    @Getter
    String name;
}