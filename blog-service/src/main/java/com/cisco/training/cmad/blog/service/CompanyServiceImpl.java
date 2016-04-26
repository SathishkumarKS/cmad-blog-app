package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.google.inject.Inject;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 4/24/16.
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;

    @Inject
    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public String registerCompany(Company company) {
        return companyDAO.save(company).toString();
    }

    @Override
    public String registerCompany(String companyName, String siteName, String subDomain, String departmentName) {
        Department dept = new Department(departmentName);
        Site acmeSite = new Site(siteName)
                            .withSubDomain(subDomain)
                            .addDepartment(dept);
        Company acme = new Company(companyName).addSite(acmeSite);

        try {
            return companyDAO.save(acme).toString();
        } catch (DuplicateKeyException dke) {
            System.err.println("Company already exists with same name");
        }
        return "Failure";
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return this.companyDAO.find().asList().stream().map(company -> {
            return new CompanyDTO(company.getId().toString(), company.getName());
        }).collect(Collectors.toCollection(ArrayList<CompanyDTO>::new));
    }

    private Company getCompany(String companyId) {
        return companyDAO.findOne("id", new ObjectId(companyId));
    }

    @Override
    public List<SiteDTO> getSites(String companyId) {
        Company company = getCompany(companyId);
        return company.getSites().stream().map(site -> {
            return new SiteDTO(companyId, site.getId().toString(), site.getName(), site.getSubDomain());
        }).collect(Collectors.toCollection(ArrayList<SiteDTO>::new));
    }

    @Override
    public List<DepartmentDTO> getDepartments(String companyId, String siteId) {
        return getCompany(companyId).getSite(new ObjectId(siteId)).getDepartments().stream().map(dept -> {
            return new DepartmentDTO(companyId, siteId, dept.getId().toString(), dept.getName());
        }).collect(Collectors.toCollection(ArrayList<DepartmentDTO>::new));
    }
}
