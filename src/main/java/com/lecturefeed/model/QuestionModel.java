package com.lecturefeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionModel {
    //null means the question is not yet handled by the server
    private Integer id = null;
    //null means the question was send anonymous
    private Integer participantId;
    private String message;
    private Integer rating = 0;
    private Long created;
    //null means the question was never closed
    private Long closed;

    private final HashSet<Integer> voters = new HashSet<>();


    public void ratingUp(Integer voterId){
        if (!voters.contains(id)){
            rating++;
            voters.add(id);
        }
    }

    public void ratingDown(Integer voterId){
        if (!voters.contains(id)){
            rating--;
            voters.add(id);
        }
    }

    public void closeQuestion(){
        if(closed == null) closed = System.currentTimeMillis();
    }
}
