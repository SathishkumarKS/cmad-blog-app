package com.cisco.training.cmad.blog.exception;

/**
 * Created by satkuppu on 27/04/16.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
