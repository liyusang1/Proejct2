package org.example.project2.domain.reply.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class ReplyIdIsInvalidException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.REPLY_ID_IS_INVALID;

    public ReplyIdIsInvalidException() {

        super(ERROR_CODE);
    }
}
