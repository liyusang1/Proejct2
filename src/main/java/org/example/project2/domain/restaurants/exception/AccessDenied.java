package org.example.project2.domain.restaurants.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class AccessDenied extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.ACCESS_REJECT;

    public AccessDenied() { super(ERROR_CODE); }
}
