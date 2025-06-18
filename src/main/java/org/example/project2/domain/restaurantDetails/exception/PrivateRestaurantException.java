package org.example.project2.domain.restaurantDetails.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class PrivateRestaurantException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.THIS_RESTAURANT_PRIVATE;

    public PrivateRestaurantException() { super(ERROR_CODE); }
}
