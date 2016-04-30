package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.google.inject.Guice;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 4/23/16.
 */
public class CompanyClient {

    @Inject
    private CompanyService companyService;

    @Inject
    private CompanyDAO companyDAO;

    public CompanyClient() {
        Guice.createInjector(new BlogModule()).injectMembers(this);
    }

    public static void main(String[] args) throws Exception {
        new CompanyClient().addCompany();
//        System.out.println(Arrays.asList("Sathish,Krishnan".split("\\s*,\\s*")));
//        System.out.println(Arrays.asList("Sathish","Krishnan")
//                .stream().collect(Collectors.joining(", ")));
//        System.out.println("companyService.getSites(ciscoId) = " + companyService.getSites("571d8f20d4a3fd35a649c2ec"));

/*
        System.out.println("companyService.getSites() = " + companyService.getSites("571d0df6d4a3fd2bc5df969d"));
        System.out.println("companyService.getDepartments() = " + companyService.getDepartments("571d0df6d4a3fd2bc5df969d","571d0df6d4a3fd2bc5df969c"));
        Company myCompany = companyService.getCompany("571cbea0d4a3fd1b8a5a6326");
        Site mySite = myCompany.getSite("18450144-1c8a-47e3-b53d-39ed3c7fc8b0");
        mySite.addDepartment(new Department("Sales"));
        dao.save(myCompany);
*/

/*

        System.out.println("mySite = " + mySite);
*/
    }

    public void addCompany() {
        Site indiaSite = new Site("India")
                .addDepartment(new Department("HR"))
                .addDepartment(new Department("Engineering"))
                .addDepartment(new Department("Support"));
        Site usSite = new Site("US")
                .addDepartment(new Department("HR"))
                .addDepartment(new Department("Sales"))
                .addDepartment(new Department("Engineering"));
        Company company = new Company("CISCO").withSubDomain(".cisco.com");
        company.addSite(indiaSite);
        company.addSite(usSite);

        String companyId = companyDAO.save(company).toString();
        System.out.println("companyId = " + companyId);
    }

}
