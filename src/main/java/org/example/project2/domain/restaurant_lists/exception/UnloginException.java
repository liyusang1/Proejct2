package org.example.project2.domain.restaurant_lists.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class UnloginException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.NOT_LOGIN_STATUS;

    public UnloginException() { super(ERROR_CODE); }
}
