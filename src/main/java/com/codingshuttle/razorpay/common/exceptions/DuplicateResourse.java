package com.codingshuttle.razorpay.common.exceptions;

import lombok.Getter;

@Getter
public class DuplicateResourse extends RuntimeException{
    private final String errorCode;
    public DuplicateResourse(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
