package org.example.project2.domain.recipes.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RecipeCookingTimeException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.COOKING_TIME_TOO_SHORT;

    public RecipeCookingTimeException() {

        super(ERROR_CODE);
    }
}
