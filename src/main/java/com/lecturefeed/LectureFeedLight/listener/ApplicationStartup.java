package com.lecturefeed.LectureFeedLight.listener;

import com.lecturefeed.LectureFeedLight.authentication.jwt.CustomAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Getter
    private final CustomAuthenticationService customAuthenticationService;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        customAuthenticationService.init();
    }
}
