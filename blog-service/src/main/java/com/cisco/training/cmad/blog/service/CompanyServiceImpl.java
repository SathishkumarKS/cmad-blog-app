package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.dto.CompanyRegistrationStatusDTO;
import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 4/24/16.
 */
@Singleton
public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO companyDAO;

    @Inject
    public CompanyServiceImpl(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public CompanyRegistrationStatusDTO registerCompany(String companyName, String subDomain, String deptName) {
        Preconditions.checkArgument(companyName != null, "Company name can't be null");
        Preconditions.checkArgument(deptName != null, "Department name can't be null");
        Department dept = new Department(deptName);
        Site acmeSite = new Site("Default Site")
                .addDepartment(dept);
        Company acme = new Company(companyName)
                .withSubDomain(subDomain)
                .addSite(acmeSite);
        String companyId = companyDAO.saveCompany(acme);
        return new CompanyRegistrationStatusDTO(companyId, acmeSite.getId().toString(), dept.getId().toString(), deptName);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return CompanyMapper.toCompanyDTOList(companyDAO.getAllCompanies());
    }

    private Optional<Company> getCompany(String companyId) {
        return Optional.ofNullable(companyDAO.getCompany(companyId));
    }

    @Override
    public List<SiteDTO> getSites(String companyId) throws DataNotFound {
        return getCompany(companyId)
                .flatMap(company -> {
                    return company.getSites();
                }).map(sites -> {
                    return CompanyMapper.toSiteDTOList(companyId, sites.values());
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
                    return company.getSite(siteId);
                }).flatMap(site -> {
                    return site.getDepartments();
                }).map(dep -> {
                    return CompanyMapper.toDepartmentDTOList(companyId, siteId, dep.values());
                }).orElseThrow(new Supplier<RuntimeException>() {
                    @Override
                    public RuntimeException get() {
                        throw new DataNotFound("No Departments found");
                    }
                });
    }

    /**
     * Created by satkuppu on 29/04/16.
     */
    private static class CompanyMapper {

        public static CompanyDTO toCompanyDTO(Company company) {
            return new CompanyDTO(company.getId(), company.getName(), company.getSubDomain());
        }

        public static List<CompanyDTO> toCompanyDTOList(List<Company> companies) {
            return companies.stream().map(company -> {
                return toCompanyDTO(company);
            }).collect(Collectors.toCollection(ArrayList<CompanyDTO>::new));
        }

        public static List<SiteDTO> toSiteDTOList(String companyId, Collection<Site> sites) {
            return sites.stream().map(site -> {
                return toSiteDTO(companyId, site);
            }).collect(Collectors.toCollection(ArrayList<SiteDTO>::new));
        }

        public static List<DepartmentDTO> toDepartmentDTOList(String companyId, String siteId, Collection<Department> departments) {
            return departments.stream().map(department -> {
                return new DepartmentDTO(companyId, siteId, department.getId().toString(), department.getName());
            }).collect(Collectors.toCollection(ArrayList<DepartmentDTO>::new));
        }

        public static SiteDTO toSiteDTO(String companyId, Site site) {
            return new SiteDTO(companyId, site.getId().toString(), site.getName());
        }
    }
}
