package com.cisco.training.cmad.blog.exception;

/**
 * Created by satkuppu on 30/04/16.
 */
public class CompanyAlreadyExists extends RuntimeException {
    public CompanyAlreadyExists(String message) {
        super(message);
    }
}
