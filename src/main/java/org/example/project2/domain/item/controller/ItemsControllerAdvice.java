package org.example.project2.domain.item.controller;

import org.example.project2.domain.item.exception.ItemIdIsInvalidException;
import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemsControllerAdvice {

    @ExceptionHandler({
            ItemIdIsInvalidException.class
            //NewException.class,
            //NewException.class
    })
    public ResponseEntity<ResponseDTO<Void>> handleItemsControllerException(ApplicationException exception) {
        HttpStatus status = exception.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, exception.getMessage()));
    }
}

