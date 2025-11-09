package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PaperStateFactory {
    private static ApplicationContext context;

    public static PaperState getState(PaperStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        return context.getBean(status.name(), PaperState.class);
    }

    @Autowired
    private void setContext(ApplicationContext applicationContext) {
        PaperStateFactory.context = applicationContext;
    }
}
