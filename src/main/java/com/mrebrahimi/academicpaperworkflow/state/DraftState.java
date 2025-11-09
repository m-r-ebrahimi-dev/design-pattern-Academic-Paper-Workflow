package com.mrebrahimi.academicpaperworkflow.state;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.exception.UnauthorizedActionException;
import com.mrebrahimi.academicpaperworkflow.state.common.BasePaperState;
import org.springframework.stereotype.Component;

@Component("DRAFT")
public class DraftState extends BasePaperState {
    @Override
    public void submit(Paper paper, User user) {
        if (!paper.getAuthorId().equals(user.getId())) {
            throw new UnauthorizedActionException("Only the author can submit the paper.");
        }
        System.out.println("Submitting paper ID: " + paper.getId());
        paper.changeState(PaperStateFactory.getState(PaperStatus.SUBMITTED));
    }

    @Override
    public PaperStatus getStatus() {
        return PaperStatus.DRAFT;
    }
}