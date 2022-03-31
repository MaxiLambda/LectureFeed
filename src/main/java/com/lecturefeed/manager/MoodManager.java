package com.lecturefeed.manager;

import com.lecturefeed.repository.service.MoodDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoodManager {
    private final MoodDBService moodDBService;
}
