package com.cisco.training.cmad.blog.exception;

/**
 * Created by satkuppu on 27/04/16.
 */
public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String message, Throwable t) {
        super(message, t);
    }
}
