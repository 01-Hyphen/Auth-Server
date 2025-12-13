package com.auth.exception;

public class EntityAlreadyExistException extends RuntimeException{

    public EntityAlreadyExistException(String entityName,String entityValue){
        super(String.format("%s already exists with name: %s",entityName,entityValue));
    }
}
