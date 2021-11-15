package com.lecturefeed.session;

import lombok.*;

import java.util.HashMap;

@Data
public class Session {
    private final HashMap<Integer,Participant> participants;
    private final int id;
    private final String sessionCode;


    public Session(Integer id){
        this.participants = new HashMap<>();
        this.id = id;
        //todo replace with actual generation
        this.sessionCode = id+"code";
    }

}
