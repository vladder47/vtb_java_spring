package com.vtb.vladislav.spring.data.lesson8.homework.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
