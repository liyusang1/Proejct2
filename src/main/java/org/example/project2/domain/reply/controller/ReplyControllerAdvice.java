package org.example.project2.domain.reply.controller;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReplyControllerAdvice {

    @ExceptionHandler({
            //NewException.class,
            //NewException.class
    })
    public ResponseEntity<ResponseDTO<Void>> handleReplyControllerException(ApplicationException exception) {
        HttpStatus status = exception.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, exception.getMessage()));
    }
}

