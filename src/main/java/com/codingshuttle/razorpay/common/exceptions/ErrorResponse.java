package com.codingshuttle.razorpay.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String errorDescription,
        String errorCode,
        LocalDateTime timestamp,
        List<FieldError> fieldErrors

) {

    public record FieldError(String field, String message) {
    }

    // utils method

    public static ErrorResponse of(String errorDescription, String errorCode, LocalDateTime timestamp, List<FieldError> fieldErrors) {
        return new ErrorResponse(errorDescription, errorCode, timestamp, fieldErrors);
    }

    public static ErrorResponse of(String errorDescription, String errorCode, LocalDateTime timestamp) {
        return new ErrorResponse(errorDescription, errorCode, timestamp, null);
    }
}
