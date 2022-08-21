package com.shtand.aggregator.task.backend.model;

public class CrmException extends RuntimeException{
    public CrmException(Exception e, String message) {
        super(message,e);
    }
}
