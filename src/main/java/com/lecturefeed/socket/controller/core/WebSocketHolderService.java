package com.lecturefeed.socket.controller.core;

import com.auth0.jwt.interfaces.Claim;
import com.lecturefeed.authentication.InetAddressSecurityService;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.manager.MoodManager;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.socket.controller.core.event.WebSocketSessionRegistrationEvent;
import com.lecturefeed.socket.controller.model.WebSocketHolderSession;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.PrincipalUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
public class WebSocketHolderService{

    private final InetAddressSecurityService inetAddressSecurityService;
    private final ParticipantManager participantManager;
    private final MoodManager moodManager;
    private final SessionDataService sessionDataService;
    private final Map<String, WebSocketHolderSession> webSocketHolderSessions = new HashMap<>();

    @EventListener
    public void onWebSocketSessionCreated(WebSocketSessionRegistrationEvent event) {
        addUnknownSession(event.getWebSocketSession());
    }

    @EventListener
    public void onSocketConnected(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        connectParticipantToSessionId(sha.getSessionId(), (UsernamePasswordAuthenticationToken) event.getUser());
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        removeSessionById(sha.getSessionId());
    }

    private void addUnknownSession(WebSocketSession webSocketSession){
        WebSocketHolderSession webSocketHolderSession = new WebSocketHolderSession();
        webSocketHolderSession.setWebSocketSession(webSocketSession);
        webSocketHolderSessions.put(webSocketSession.getId(), webSocketHolderSession);
    }

    private void connectParticipantToSessionId(String webSocketSessionId, UsernamePasswordAuthenticationToken principal){
        if(webSocketHolderSessions.containsKey(webSocketSessionId)){
            Claim claimId = PrincipalUtils.getClaim("id", principal);
            if(claimId != null) {
                int participantId = claimId.asInt();
                webSocketHolderSessions.get(webSocketSessionId).setParticipantId(participantId);
                participantManager.updateConnectionStatusByParticipantId(participantId, true);
                sendConnectionStatusByParticipantId(participantId);
            }else{
                webSocketHolderSessions.remove(webSocketSessionId);
            }
        }
    }

    private void sendConnectionStatusByParticipantId(int participantId){
        int sessionId = participantManager.getSessionIdByParticipantId(participantId);
        List<Participant> participants = participantManager.getParticipantsBySessionId(sessionId);
        if(participants.size() == 0) moodManager.addMoodValueToSession(sessionId, 0, 0);
        sessionDataService.sendConnectionStatus(sessionId, participants);
    }

    private WebSocketHolderSession getWebSocketHolderSessionByParticipantId(int participantId){
        List<WebSocketHolderSession> sessions = webSocketHolderSessions.values().stream().filter(s -> s.getParticipantId() == participantId).toList();
        return sessions.size() == 1? sessions.get(0): null;
    }

    public void killConnectionByParticipantId(int participantId) {
        WebSocketHolderSession webSocketHolderSession = this.getWebSocketHolderSessionByParticipantId(participantId);
        if(webSocketHolderSession != null){
            try {
                webSocketHolderSession.getWebSocketSession().close(CloseStatus.NO_STATUS_CODE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void blockRemoteAddrByParticipantId(int participantId){
        WebSocketHolderSession webSocketHolderSession = getWebSocketHolderSessionByParticipantId(participantId);
        if (webSocketHolderSession != null){
            blockRemoteAddrByWebSessionId(webSocketHolderSession.getWebSocketSession().getId());
        }
    }

    private void blockRemoteAddrByWebSessionId(String webSessionId){
        inetAddressSecurityService.blockInetAddress(webSocketHolderSessions.get(webSessionId).getWebSocketSession().getRemoteAddress().getAddress());
    }

    private void removeSessionById(String webSessionId){
        if(webSocketHolderSessions.containsKey(webSessionId)){
            WebSocketHolderSession webSocketHolderSession = webSocketHolderSessions.get(webSessionId);
            participantManager.updateConnectionStatusByParticipantId(webSocketHolderSession.getParticipantId(), false);
            sendConnectionStatusByParticipantId(webSocketHolderSession.getParticipantId());
            webSocketHolderSessions.remove(webSessionId);
        }
    }

}
