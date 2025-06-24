package org.example.project2.domain.recipes.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class BookmarkAlreadyDeactivatedException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.BOOKMARK_ALREADY_DEACTIVATED;

    public BookmarkAlreadyDeactivatedException() {

        super(ERROR_CODE);
    }
}
