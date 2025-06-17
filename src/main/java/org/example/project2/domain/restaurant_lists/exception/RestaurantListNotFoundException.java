package org.example.project2.domain.restaurant_lists.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RestaurantListNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.RESTAURANTLIST_NOT_FOUND;

    public RestaurantListNotFoundException() { super(ERROR_CODE); }
}
