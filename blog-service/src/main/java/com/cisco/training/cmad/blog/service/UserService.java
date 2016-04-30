package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.UserAlreadyExists;

/**
 * Created by satkuppu on 25/04/16.
 */
public interface UserService {
    public String registerUser(UserRegistrationDTO userRegistration) throws UserAlreadyExists;
    public Boolean authenticateUser(UserAuthDTO userAuthDTO);
}
