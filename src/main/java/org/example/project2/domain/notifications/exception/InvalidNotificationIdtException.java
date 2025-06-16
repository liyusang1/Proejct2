package org.example.project2.domain.notifications.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class InvalidNotificationIdtException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.NOTIFICATION_ID_IS_INVALID;

    public InvalidNotificationIdtException() {

        super(ERROR_CODE);
    }
}
