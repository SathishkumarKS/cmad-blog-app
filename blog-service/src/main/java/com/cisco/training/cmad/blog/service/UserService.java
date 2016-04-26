package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;

/**
 * Created by satkuppu on 25/04/16.
 */
public interface UserService {
    public String registerUser(UserRegistrationDTO userRegistration);
}
