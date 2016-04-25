package com.cisco.training.cmad.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by satkuppu on 4/25/16.
 */
@Data @AllArgsConstructor
public class SiteDTO {
    private String companyId;
    private String siteId;
    private String siteName;
    private String subDomain;
}
