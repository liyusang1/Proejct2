package org.example.project2.domain.recipes.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class BookmarkAlreadyExistsException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.BOOKMARK_ALREADY_EXITS;

    public BookmarkAlreadyExistsException() {

        super(ERROR_CODE);
    }
}
