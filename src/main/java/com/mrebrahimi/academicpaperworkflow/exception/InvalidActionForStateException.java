package com.mrebrahimi.academicpaperworkflow.exception;

import com.mrebrahimi.academicpaperworkflow.domain.PaperStatus;

public class InvalidActionForStateException extends RuntimeException {
    public InvalidActionForStateException(String action, PaperStatus status) {
        super(String.format("Action '%s' is not allowed in state '%s'.", action, status));
    }
}