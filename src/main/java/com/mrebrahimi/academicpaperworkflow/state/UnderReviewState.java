package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.domain.Role;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.exception.UnauthorizedActionException;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("UNDER_REVIEW")
public class UnderReviewState extends BasePaperState {

    @Override
    public void accept(Paper paper, User editor) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can accept papers.");
        }
        System.out.println("Paper ID: " + paper.getId() + " has been ACCEPTED by editor " + editor.getUsername());
        paper.changeState(PaperStateFactory.getState(PaperStatus.ACCEPTED));
    }

    @Override
    public void reject(Paper paper, User editor) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can reject papers.");
        }
        System.out.println("Paper ID: " + paper.getId() + " has been REJECTED by editor " + editor.getUsername());
        paper.changeState(PaperStateFactory.getState(PaperStatus.REJECTED));
    }

    @Override
    public void requestRevision(Paper paper, User editor) {
        if (editor.getRole() != Role.EDITOR) {
            throw new UnauthorizedActionException("Only editors can request revisions.");
        }
        System.out.println("Editor " + editor.getUsername() + " requested revisions for paper ID: " + paper.getId());
        paper.changeState(PaperStateFactory.getState(PaperStatus.NEEDS_REVISION));
    }

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.UNDER_REVIEW;
    }
}
