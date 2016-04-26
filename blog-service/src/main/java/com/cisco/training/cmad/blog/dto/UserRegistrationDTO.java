package com.cisco.training.cmad.blog.dto;

import lombok.Data;

/**
 * Created by satkuppu on 4/25/16.
 */
@Data
public class UserRegistrationDTO {

    private String userName;
    private String password;
    private String email;
    private String first;
    private String last;
    private String companyId;
    private String companyName;
    private String siteId;
    private String siteName;
    private String deptId;
    private String deptName;
}
