package com.cisco.training.cmad.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by satkuppu on 4/25/16.
 */
@Data
@AllArgsConstructor
public class CompanyRegistrationStatusDTO {
    private String companyId;
    private String siteId;
    private String deptId;
    private String deptName;
}
