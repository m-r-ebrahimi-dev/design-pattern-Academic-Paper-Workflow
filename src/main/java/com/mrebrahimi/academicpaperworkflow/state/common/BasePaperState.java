package com.mrebrahimi.academicpaperworkflow.state.common;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.exception.InvalidActionForStateException;
import com.mrebrahimi.academicpaperworkflow.state.PaperState;

public abstract class BasePaperState implements PaperState {
    @Override
    public void submit(Paper paper, User user) {
        throw new InvalidActionForStateException("submit", getStatus());
    }

    @Override
    public void assignToReviewer(Paper paper, User editor, User reviewer) {
        throw new InvalidActionForStateException("assignToReviewer", getStatus());
    }

    @Override
    public void accept(Paper paper, User editor) {
        throw new InvalidActionForStateException("accept", getStatus());
    }

    @Override
    public void reject(Paper paper, User editor) {
        throw new InvalidActionForStateException("reject", getStatus());
    }

    @Override
    public void publish(Paper paper, User editor) {
        throw new InvalidActionForStateException("publish", getStatus());
    }

    @Override
    public void requestRevision(Paper paper, User editor) {
        throw new InvalidActionForStateException("requestRevision", getStatus());
    }
}