package com.codingshuttle.razorpay.common.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private Object identifier;

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName + " not found with " + identifier);
        this.resourceName = resourceName;
        this.identifier = identifier;
    }
}
