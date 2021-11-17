package com.lecturefeed.model;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;

public class SessionManager {

    private Map<Integer, Session> sessions;

    public Session createNewSession(int sessionId) throws SessionAlreadyExistsException {
        if (!sessionExists(sessionId))
        {
            String sessionCode = generateNewSessionCode();
            Session session =  new Session(sessionCode,sessionId);

            sessions.put(sessionId, session);

            return session;
        }
        throw new SessionAlreadyExistsException(sessionId);
    }

    private boolean sessionExists(int sessionId)
    {
        for (Integer currentSessionId: sessions.keySet())
        {
            if (sessionId == currentSessionId)
            {
                return true;
            }
        }
        return false;
    }

    private String generateNewSessionCode()
    {
        String sessionCode = generateRandomStringCode();
        boolean sessionCodeDuplicateFound;

        do {
            sessionCodeDuplicateFound = false;
            for (Session session : sessions.values()) {
                String currentSessionCode = session.getSessionCode();

                if (currentSessionCode.equals(sessionCode)) {
                    sessionCodeDuplicateFound = true;
                }
            }

            if (sessionCodeDuplicateFound){
                sessionCode = generateRandomStringCode();
            }

        } while(sessionCodeDuplicateFound);

        return sessionCode;
    }


    private String generateRandomStringCode()
    {
        byte[] array = new byte[8]; // length is bounded by 8
        new Random().nextBytes(array);
        String code = new String(array, Charset.forName("UTF-8"));
        return code;
    }
}
