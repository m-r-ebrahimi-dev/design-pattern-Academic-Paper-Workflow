package com.mrebrahimi.academicpaperworkflow.state;


import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;

public interface PaperState {
    void submit(Paper paper, User user);

    void assignToReviewer(Paper paper, User editor, User reviewer);

    void accept(Paper paper, User editor);

    void reject(Paper paper, User editor);

    void requestRevision(Paper paper, User editor);

    PaperStatus getStatus();
}
