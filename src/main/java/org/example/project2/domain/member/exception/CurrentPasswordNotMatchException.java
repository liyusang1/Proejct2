package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class CurrentPasswordNotMatchException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.CURRENT_PASSWORD_NOT_MATCH;

    public CurrentPasswordNotMatchException(){super(ERROR_CODE);}
}
