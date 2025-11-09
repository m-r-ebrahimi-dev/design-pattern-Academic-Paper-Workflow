package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.exception.UnauthorizedActionException;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("NEEDS_REVISION")
public class NeedsRevisionState extends BasePaperState {

    @Override
    public void submit(Paper paper, User user) {
        if (!paper.getAuthorId().equals(user.getId())) {
            throw new UnauthorizedActionException("Only the original author can re-submit the paper.");
        }
        System.out.println("Paper ID: " + paper.getId() + " has been re-submitted by the author after revisions.");
        paper.changeState(PaperStateFactory.getState(PaperStatus.UNDER_REVIEW));
    }

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.NEEDS_REVISION;
    }
}