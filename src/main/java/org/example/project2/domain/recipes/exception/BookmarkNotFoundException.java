package org.example.project2.domain.recipes.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class BookmarkNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.BOOKMARK_NOT_FOUND;

    public BookmarkNotFoundException() {

        super(ERROR_CODE);
    }
}
