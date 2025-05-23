package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class UserInvalidAccessException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_INVALID_ACCESS;

    public UserInvalidAccessException() {
        super(ERROR_CODE);
    }
}
