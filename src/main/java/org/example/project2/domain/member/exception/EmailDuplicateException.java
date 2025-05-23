package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class EmailDuplicateException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_ALREADY_REGISTERED;

    public EmailDuplicateException() {

        super(ERROR_CODE);
    }
}
