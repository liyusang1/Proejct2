package org.example.project2.domain.item.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class ItemIdIsInvalidException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.ITEM_ID_IS_INVALID;

    public ItemIdIsInvalidException() {

        super(ERROR_CODE);
    }
}
