package com.cisco.training.cmad.blog.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

/**
 * Created by satkuppu on 25/04/16.
 */
@Data
public class UserDepartment {
    private ObjectId companyId;
    private String companyName;

    private ObjectId siteId;
    private String siteName;

    private ObjectId departmentId;
    private String departmentName;

    public UserDepartment() {
    }

    public UserDepartment(ObjectId companyId, ObjectId siteId, ObjectId departmentId) {
        this.companyId = companyId;
        this.siteId = siteId;
        this.departmentId = departmentId;
    }
}
