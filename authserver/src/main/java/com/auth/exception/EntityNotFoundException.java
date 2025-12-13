package com.auth.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String userName, String field) {
        super(entity + " not found with the "+field+" " + userName);
    }
}