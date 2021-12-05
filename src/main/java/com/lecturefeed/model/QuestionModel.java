package com.lecturefeed.model;

import com.lecturefeed.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class QuestionModel {
    //null means the question is not yet handled by the server
    private Integer id = null;
    //null means the question was send anonymous
    private final Integer participantId;
    private final String message;
    private Integer rating = 0;
    private final Long created;
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
