package org.example.project2.global.exception;


public class PermissionDeniedException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PERMISSION_DENIED;

    public PermissionDeniedException() {
        super(ERROR_CODE);
    }
}
