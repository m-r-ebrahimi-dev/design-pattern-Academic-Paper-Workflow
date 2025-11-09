package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.exception.UnauthorizedActionException;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("SUBMITTED")
public class SubmittedState extends BasePaperState {
    @Override
    public void assignToReviewer(Paper paper, User editor, User reviewer) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can assign papers to reviewers.");
        }
        System.out.println("Assigning paper " + paper.getId() + " to reviewer " + reviewer.getId());
        paper.changeState(PaperStateFactory.getState(PaperStatus.UNDER_REVIEW));
    }

    // You can also implement reject here...
    @Override
    public void reject(Paper paper, User editor) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can reject papers.");
        }
        System.out.println("Rejecting paper ID: " + paper.getId());
        paper.changeState(PaperStateFactory.getState(PaperStatus.REJECTED));
    }

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.SUBMITTED;
    }
}
