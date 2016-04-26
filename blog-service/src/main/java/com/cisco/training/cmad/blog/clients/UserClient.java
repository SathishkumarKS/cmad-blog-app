package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.service.UserService;
import com.cisco.training.cmad.blog.service.UserServiceImpl;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserClient {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserDAO(new MorphiaService().getDatastore()));
        UserRegistrationDTO userRegistration = new UserRegistrationDTO();
        userRegistration.setUserName("satkuppu");
        userRegistration.setPassword("Cisco_123");
        userRegistration.setEmail("satkuppu@cisco.com");
        userRegistration.setFirst("Sathishkumar");
        userRegistration.setLast("Kuppuswami");
        userRegistration.setCompanyId("571dff893c3ed49615899095");
        userRegistration.setCompanyName("");
        userRegistration.setSiteId("571dff893c3ed49615899093");
        userRegistration.setSiteName("");
        userRegistration.setDeptId("571dff893c3ed49615899091");
        userRegistration.setDeptName("");
        userService.registerUser(userRegistration);
    }
}
