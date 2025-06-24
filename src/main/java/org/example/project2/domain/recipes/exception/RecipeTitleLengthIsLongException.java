package org.example.project2.domain.recipes.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RecipeTitleLengthIsLongException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.TITLE_LENGTH_EXCEEDED;

    public RecipeTitleLengthIsLongException() {

        super(ERROR_CODE);
    }
}
