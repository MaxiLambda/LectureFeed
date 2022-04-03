package com.lecturefeed.manager;

import com.lecturefeed.entity.model.MoodEntity;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.service.MoodDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MoodManager {
    private final MoodDBService moodDBService;
    private final SessionManager sessionManager;
    private final ParticipantManager participantManager;
    private final SessionDataService sessionDataService;

    private final Map<Integer, Map<Integer, Integer>> sessionsParticipantMoodValueCache = new HashMap<>();

    public MoodEntity addMoodValueToSession(int sessionId, int moodValue, int participantsCount){
        MoodEntity moodEntity = MoodEntity.builder().value(moodValue).participantsCount(participantsCount).timestamp(new Date().getTime()).build();
        Session session = sessionManager.getSessionById(sessionId);
        moodDBService.save(moodEntity);
        session.getMoodEntities().add(moodEntity);
        sessionManager.saveSession(session);
        sessionDataService.sendMood(sessionId, moodEntity.getValue());
        return moodEntity;
    }

    private Map<Integer, Integer> getParticipantMoodValueCacheBySessionId(int sessionId){
        if(sessionManager.isSessionClosed(sessionId)) return null;
        List<Integer> connectedParticipantIds = participantManager.getConnectedParticipantsBySessionId(sessionId).stream().map(Participant::getId).toList();
        if(!sessionsParticipantMoodValueCache.containsKey(sessionId)){
            Map<Integer, Integer> cache = new HashMap<>();
            connectedParticipantIds.forEach(participantId -> cache.put(participantId, 0));
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }else{
            Map<Integer, Integer> cache = sessionsParticipantMoodValueCache.get(sessionId);
            Set<Integer> cachedParticipantIds = cache.keySet();
            cachedParticipantIds.forEach(participantId -> {
                if(!connectedParticipantIds.contains(participantId)){
                    cache.remove(participantId);
                }
            });
            connectedParticipantIds.forEach(participantId -> {
                if(!cachedParticipantIds.contains(participantId)){
                    cache.put(participantId, 0);
                }
            });
            sessionsParticipantMoodValueCache.put(sessionId, cache);
        }
        return sessionsParticipantMoodValueCache.get(sessionId);
    }

    public MoodEntity createCalculatedMoodValue(int sessionId, int participantId, int moodValue){
        Map<Integer, Integer> cache = getParticipantMoodValueCacheBySessionId(sessionId);
        if(cache == null || !cache.containsKey(participantId)) return null;
        cache.put(participantId, moodValue);
        sessionsParticipantMoodValueCache.put(sessionId, cache);
        if(cache.size() == 0) return addMoodValueToSession(sessionId, 0, 0);
        int averageValue = cache.values().stream().mapToInt(Integer::intValue).sum() / cache.size();
        return addMoodValueToSession(sessionId, averageValue, cache.size());
    }


}
