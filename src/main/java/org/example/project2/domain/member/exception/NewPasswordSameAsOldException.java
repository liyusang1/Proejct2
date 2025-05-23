package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class NewPasswordSameAsOldException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.NEW_PASSWORD_SAME_AS_OLD;

    public NewPasswordSameAsOldException() {
        super(ERROR_CODE);
    }
}
