package org.example.project2.domain.restaurant_lists.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class UpdateAccessDenied extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.ACCESS_REJECT;

    public UpdateAccessDenied() { super(ERROR_CODE); }
}
