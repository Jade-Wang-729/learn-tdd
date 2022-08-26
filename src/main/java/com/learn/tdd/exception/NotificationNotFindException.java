package com.learn.tdd.exception;

public class NotificationNotFindException extends RuntimeException{
    public NotificationNotFindException(String message) {
        super(message);
    }
}
