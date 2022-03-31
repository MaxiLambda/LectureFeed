package com.lecturefeed.listener;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.core.HomeDirHandler;
import com.lecturefeed.manager.SessionManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final SessionManager sessionManager;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        sessionManager.closeAllOpenSessions();
        HomeDirHandler.createHomeDir();
        CustomAuthenticationService.init();
    }
}
