package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("PUBLISHED")
public class PublishedState extends BasePaperState {

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.PUBLISHED;
    }
}
