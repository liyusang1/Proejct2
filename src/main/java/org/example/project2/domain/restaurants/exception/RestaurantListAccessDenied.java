package org.example.project2.domain.restaurants.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class RestaurantListAccessDenied extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.RESTAURANT_LIST_ACCESS_DENIED;

    public RestaurantListAccessDenied() { super(ERROR_CODE); }
}
