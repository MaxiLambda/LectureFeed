package com.lecturefeed.bootstrap;

import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.utils.QuestionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnLoad implements CommandLineRunner {

    private final ParticipantManager participantManager;

    @Override
    public void run(String... args) throws Exception {
        participantManager.addHiddenParticipant();
        System.out.println(QuestionUtils.HIDDEN_PARTICIPANT.getId());
    }
}
