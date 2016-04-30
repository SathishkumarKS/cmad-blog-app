package com.cisco.training.cmad.blog.exception;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
