package com.lecturefeed.listener;

import com.lecturefeed.utils.RunTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ApplicationOnStart implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        RunTimeUtils.openBrowser();
    }
}
