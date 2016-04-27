package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.UserService;
import com.cisco.training.cmad.blog.service.UserServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserClient {
    @Inject
    private UserService userService;

    public UserClient() {
        Guice.createInjector(new BlogModule()).injectMembers(this);    }

    public static void main(String[] args) {
        new UserClient().authUser();
    }

    private void registerUser() {
        UserRegistrationDTO userRegistration = new UserRegistrationDTO();
        userRegistration.setUserName("krishyn");
        userRegistration.setPassword("Cisco_123");
        userRegistration.setEmail("krishyn@cisco.com");
        userRegistration.setFirst("Krishnan");
        userRegistration.setLast("Yn");
        userRegistration.setCompanyId("571dff893c3ed49615899095");
        userRegistration.setCompanyName("");
        userRegistration.setSiteId("571dff893c3ed49615899093");
        userRegistration.setSiteName("");
        userRegistration.setDeptId("571dff893c3ed49615899091");
        userRegistration.setDeptName("");
        userService.registerUser(userRegistration);
    }

    private void authUser() {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUserName("Jafardeen");
        userAuthDTO.setPassword("sag_123");
        System.out.println(userService.authenticateUser(userAuthDTO));
    }
}
