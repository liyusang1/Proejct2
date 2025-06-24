package org.example.project2.domain.recipes.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RecipeDescriptionLengthIsLongException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DESCRIPTION_LENGTH_EXCEEDED;

    public RecipeDescriptionLengthIsLongException() {

        super(ERROR_CODE);
    }
}
