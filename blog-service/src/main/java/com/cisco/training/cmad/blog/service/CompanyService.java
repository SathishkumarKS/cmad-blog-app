package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;

import java.util.List;

/**
 * Created by satkuppu on 4/23/16.
 */
public interface CompanyService {

    public String registerCompany(String companyName, String siteName, String departmentName);
    public List<CompanyDTO> getAllCompanies();
    public List<SiteDTO> getSites(String companyId) throws DataNotFound;
    public List<DepartmentDTO> getDepartments(String companyId, String siteId);
}
