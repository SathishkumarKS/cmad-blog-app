package com.cisco.training.cmad.blog.exception;

/**
 * Created by satkuppu on 27/04/16.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
