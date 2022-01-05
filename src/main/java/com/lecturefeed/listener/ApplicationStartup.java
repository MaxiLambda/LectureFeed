package com.lecturefeed.listener;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.core.HomeDirHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        HomeDirHandler.createHomeDir();
        CustomAuthenticationService.init();
    }
}
