package org.example.project2.domain.restaurants.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RestaurantNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.RESTAURANT_NOT_FOUND;

    public RestaurantNotFoundException() { super(ERROR_CODE); }
}
