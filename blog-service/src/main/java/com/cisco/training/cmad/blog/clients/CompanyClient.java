package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.google.inject.Guice;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by satkuppu on 4/23/16.
 */
public class CompanyClient {

    @Inject
    private CompanyService companyService;

    public CompanyClient() {
        Guice.createInjector(new BlogModule()).injectMembers(this);
    }

    public static void main(String[] args) throws Exception {
        new CompanyClient().addCompany();
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
        Site indiaSite = new Site("India").withSubDomain("in");
//                .addDepartment(hrDept)
//                .addDepartment(salesDept)
//                .addDepartment(enggDept);
        Site ukSite = new Site("US").withSubDomain("com")
                .addDepartment(new Department("HR"))
                .addDepartment(new Department("Engineering"));
        Company softwareAG = new Company("Amazon");
        softwareAG.addSite(indiaSite);
//        softwareAG.addSite(ukSite);

        String companyId = companyService.registerCompany(softwareAG);
        System.out.println("companyId = " + companyId);
    }

}
