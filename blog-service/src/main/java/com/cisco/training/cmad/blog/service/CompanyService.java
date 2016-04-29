package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.DataNotFoundException;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Site;

import java.util.List;
import java.util.Optional;

/**
 * Created by satkuppu on 4/23/16.
 */
public interface CompanyService {

    public String registerCompany(Company company);
    public String registerCompany(String companyName, String siteName, String subDomain, String departmentName);
    public List<CompanyDTO> getAllCompanies();
    public List<SiteDTO> getSites(String companyId) throws DataNotFoundException ;
    public List<DepartmentDTO> getDepartments(String companyId, String siteId);
}
