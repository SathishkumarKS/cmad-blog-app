package com.cisco.training.cmad.blog.dto;

import lombok.Data;

/**
 * Created by satkuppu on 30/04/16.
 */
@Data
public class UserDTO {
    private String id;
    private String first;
    private String last;
    private String userName;
    private String email;
    private String companyId;
    private String companyName;
    private String subdomain;
    private String siteId;
    private String siteName;
    private String departmentId;
    private String departmentName;
}
