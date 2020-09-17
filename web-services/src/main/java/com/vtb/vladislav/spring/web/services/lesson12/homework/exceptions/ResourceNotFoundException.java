package com.vtb.vladislav.spring.web.services.lesson12.homework.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
