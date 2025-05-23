package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class UserNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException() {
        super(ERROR_CODE);
    }
}
