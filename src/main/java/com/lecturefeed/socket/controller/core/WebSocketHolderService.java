package com.lecturefeed.socket.controller.core;

import com.auth0.jwt.interfaces.Claim;
import com.lecturefeed.authentication.InetAddressSecurityService;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.socket.controller.model.WebSocketHolderSession;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.PrincipalUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Service
public class WebSocketHolderService {

    private final InetAddressSecurityService inetAddressSecurityService;
    private final ParticipantManager participantManager;
    private final SessionDataService sessionDataService;
    private final Map<String, WebSocketHolderSession> webSocketHolderSessions = new HashMap<>();


    public void addUnknownSession(WebSocketSession webSocketSession){
        WebSocketHolderSession webSocketHolderSession = new WebSocketHolderSession();
        webSocketHolderSession.setWebSocketSession(webSocketSession);
        webSocketHolderSessions.put(webSocketSession.getId(), webSocketHolderSession);
    }

    public void connectParticipantToSessionId(String webSocketSessionId, UsernamePasswordAuthenticationToken principal){
        if(webSocketHolderSessions.containsKey(webSocketSessionId)){
            Claim claimId = PrincipalUtils.getClaim("id", principal);
            if(claimId != null) {
                int participantId = claimId.asInt();
                webSocketHolderSessions.get(webSocketSessionId).setParticipantId(participantId);
                participantManager.updateConnectionStatusByParticipantId(participantId, true);
            }
        }
    }

    private void sendConnectionStatusByParticipantId(int participantId){
        int sessionId = participantManager.getSessionIdByParticipantId(participantId);
        sessionDataService.sendConnectionStatus(sessionId, participantManager.getConnectionStatusByParticipants(sessionId));
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

    public void blockRemoteAddrByWebSessionId(String webSessionId){
        inetAddressSecurityService.blockInetAddress(webSocketHolderSessions.get(webSessionId).getWebSocketSession().getRemoteAddress().getAddress());
    }

    public void removeSessionById(String webSessionId){
        if(webSocketHolderSessions.containsKey(webSessionId)){
            WebSocketHolderSession webSocketHolderSession = webSocketHolderSessions.get(webSessionId);
            participantManager.updateConnectionStatusByParticipantId(webSocketHolderSession.getParticipantId(), false);
            sendConnectionStatusByParticipantId(webSocketHolderSession.getParticipantId());
            webSocketHolderSessions.remove(webSessionId);
        }
    }



}
