package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import jakarta.persistence.PostLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaperStateListener {

    private static PaperStateFactory stateFactory;

    @Autowired
    public void init(PaperStateFactory factory) {
        PaperStateListener.stateFactory = factory;
    }

    @PostLoad
    public void mapState(Paper paper) {
        PaperState state = stateFactory.getState(paper.getStatus());
        paper.setCurrentState(state);
    }
}
