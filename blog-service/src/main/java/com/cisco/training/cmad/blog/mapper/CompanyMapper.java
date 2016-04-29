package com.cisco.training.cmad.blog.mapper;

import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 29/04/16.
 */
public class CompanyMapper {

    public CompanyDTO toCompanyDTO(Company company) {
        return new CompanyDTO(company.getId().toString(), company.getName());
    }

    public List<CompanyDTO> toCompanyDTOList(List<Company> companies) {
        return companies.stream().map(company -> {
            return toCompanyDTO(company);
        }).collect(Collectors.toCollection(ArrayList<CompanyDTO>::new));
    }

    public List<SiteDTO> toSiteDTOList(String companyId, Collection<Site> sites) {
        return sites.stream().map(site -> {
            return toSiteDTO(companyId, site);
        }).collect(Collectors.toCollection(ArrayList<SiteDTO>::new));
    }

    public List<DepartmentDTO> toDepartmentDTOList(String companyId, String siteId, Collection<Department> departments) {
        return departments.stream().map(department -> {
            return new DepartmentDTO(companyId, siteId, department.getId().toString(), department.getName());
        }).collect(Collectors.toCollection(ArrayList<DepartmentDTO>::new));
    }

    public SiteDTO toSiteDTO(String companyId, Site site) {
        return new SiteDTO(companyId, site.getId().toString(), site.getName(), site.getSubDomain());
    }
}
