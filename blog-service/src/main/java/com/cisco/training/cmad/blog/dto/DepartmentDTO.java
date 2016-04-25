package com.cisco.training.cmad.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by satkuppu on 4/25/16.
 */
@Data
@AllArgsConstructor
public class DepartmentDTO {
    private String companyId;
    private String siteId;
    private String id;
    private String deptName;
}
