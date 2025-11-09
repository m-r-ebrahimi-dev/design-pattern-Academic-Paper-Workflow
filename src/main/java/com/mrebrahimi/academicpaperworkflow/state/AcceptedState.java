package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.domain.Role;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.exception.UnauthorizedActionException;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("ACCEPTED")
public class AcceptedState extends BasePaperState {
    @Override
    public void publish(Paper paper, User editor) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can publish papers.");
        }
        System.out.println("Publishing paper ID: " + paper.getId() + ". It is now publicly available.");
        paper.changeState(PaperStateFactory.getState(PaperStatus.PUBLISHED));
    }

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.ACCEPTED;
    }
}
