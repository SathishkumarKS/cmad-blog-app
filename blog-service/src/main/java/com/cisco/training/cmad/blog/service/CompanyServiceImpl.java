package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.DataNotFoundException;
import com.cisco.training.cmad.blog.mapper.CompanyMapper;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.google.inject.Inject;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by satkuppu on 4/24/16.
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;
    private CompanyMapper companyMapper;

    @Inject
    public CompanyServiceImpl(CompanyDAO companyDAO, CompanyMapper companyMapper) {
        this.companyDAO = companyDAO;
        this.companyMapper = companyMapper;
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
        return companyMapper.toCompanyDTOList(this.companyDAO.find().asList());
    }

    private Company getCompany(String companyId) {
        return Optional.ofNullable(companyDAO.findOne("id", new ObjectId(companyId)))
                .orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFoundException("Company not found");
                    }
                });
    }

    private Site getSite(String companyId, String siteId) {
        return getCompany(companyId).getSite(new ObjectId(siteId))
                .orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFoundException("Site not found");
                    }
                });
    }

    @Override
    public List<SiteDTO> getSites(String companyId) throws DataNotFoundException {
        return getCompany(companyId).getSites()
                .map(sites -> {
                    System.out.println("sites = " + sites);
                    return companyMapper.toSiteDTOList(companyId, sites.values());
                }).orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFoundException("No Sites found");
                    }
                });
    }

    @Override
    public List<DepartmentDTO> getDepartments(String companyId, String siteId) {
        return getSite(companyId, siteId).getDepartments()
                .map(dep -> {
                    return companyMapper.toDepartmentDTOList(companyId, siteId, dep.values());
                }).orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFoundException("No Departments found");
                    }
                });
    }
}
