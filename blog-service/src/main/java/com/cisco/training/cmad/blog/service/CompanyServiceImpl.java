package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.CompanyRegistrationStatusDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.mapper.CompanyMapper;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.google.inject.Inject;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
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
    public CompanyRegistrationStatusDTO registerCompany(String companyName, String subDomain, String deptName) {
        Department dept = new Department(deptName);
        Site acmeSite = new Site("Default Site")
                .addDepartment(dept);
        Company acme = new Company(companyName)
                .withSubDomain(subDomain)
                .addSite(acmeSite);
        try {
            Key<Company> companyId = companyDAO.save(acme);
            return new CompanyRegistrationStatusDTO(companyId.getId().toString(), acmeSite.getId().toString(), dept.getId().toString(), deptName);
        } catch (DuplicateKeyException dke) {
            throw new CompanyAlreadyExists("Company already exists with same name");
        }
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        final QueryResults<Company> companies = this.companyDAO.find();
        if(companies != null) {
            return companyMapper.toCompanyDTOList(companies.asList());
        }
        throw new DataNotFound("No Companies found");
    }

    private Optional<Company> getCompany(String companyId) {
        return Optional.ofNullable(companyDAO.findOne("id", new ObjectId(companyId)));
    }

    @Override
    public List<SiteDTO> getSites(String companyId) throws DataNotFound {
        return getCompany(companyId)
                .flatMap(company -> {
                    return company.getSites();
                }).map(sites -> {
                    return companyMapper.toSiteDTOList(companyId, sites.values());
                }).orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFound("No Sites found");
                    }
                });
    }

    @Override
    public List<DepartmentDTO> getDepartments(String companyId, String siteId) {
        return getCompany(companyId)
                .flatMap(company -> {
                    return company.getSite(new ObjectId(siteId));
                }).flatMap(site -> {
                    return site.getDepartments();
                }).map(dep -> {
                    return companyMapper.toDepartmentDTOList(companyId, siteId, dep.values());
                }).orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFound("No Departments found");
                    }
                });
    }
}
