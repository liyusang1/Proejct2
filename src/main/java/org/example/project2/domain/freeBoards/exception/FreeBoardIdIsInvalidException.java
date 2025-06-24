package org.example.project2.domain.freeBoards.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class FreeBoardIdIsInvalidException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.FREE_BOARD_ID_IS_INVALID;

    public FreeBoardIdIsInvalidException() {
        super(ERROR_CODE);
    }
}