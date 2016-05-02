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
    private boolean isCompany;
    private String companyId;
    private String companyName;
    private String subdomain;
    private String siteId;
    private String siteName;
    private String deptId;
    private String deptName;

    public boolean getIsCompany() {
        return this.isCompany;
    }

    public void setIsCompany(boolean isCompany) {
        this.isCompany = isCompany;
    }

}
