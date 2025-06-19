package org.example.project2.domain.follow.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class SelfFollowNotAllowedException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SELF_FOLLOW_NOT_ALLOWED;

    public SelfFollowNotAllowedException() {

        super(ERROR_CODE);
    }
}
