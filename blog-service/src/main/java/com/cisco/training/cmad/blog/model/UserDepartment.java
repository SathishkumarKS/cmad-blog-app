package com.cisco.training.cmad.blog.model;

import lombok.Data;

/**
 * Created by satkuppu on 25/04/16.
 */
@Data
public class UserDepartment {
    private String companyId;
    private String companyName;

    private String siteId;
    private String siteName;

    private String departmentId;
    private String departmentName;

    public UserDepartment() {
    }

    public UserDepartment(String companyId, String siteId, String departmentId) {
        this.companyId = companyId;
        this.siteId = siteId;
        this.departmentId = departmentId;
    }
}
