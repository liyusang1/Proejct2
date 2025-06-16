package org.example.project2.domain.recipes.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RecipeIdIsInvalidException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.RECIPE_ID_IS_INVALID;

    public RecipeIdIsInvalidException() {

        super(ERROR_CODE);
    }
}
