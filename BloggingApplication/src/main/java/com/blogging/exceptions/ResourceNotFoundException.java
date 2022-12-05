package com.blogging.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Integer userId) {
        super(String.format("%s not found with %s : %d",resourceName,fieldName,userId));
    }
}
