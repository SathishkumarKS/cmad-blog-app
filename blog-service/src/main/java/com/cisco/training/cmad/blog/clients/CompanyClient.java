package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import lombok.Data;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

/**
 * Created by satkuppu on 4/23/16.
 */
public class CompanyClient {

    @Inject
    private CompanyService companyService;

    public CompanyClient() {
        Guice.createInjector(new BlogModule()).injectMembers(this);
    }

    public static void main(String[] args) {
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
        Department dept = new Department("Consulting");
        Department dept1 = new Department("Engineering");
        Site indiaSite = new Site("India").withSubDomain("in").addDepartment(dept).addDepartment(dept1);
        Site ukSite = new Site("Germany").withSubDomain("de").addDepartment(dept).addDepartment(dept1);
        Company softwareAG = new Company("Software AG");
        softwareAG.addSite(indiaSite);
        softwareAG.addSite(ukSite);

        String companyId = companyService.registerCompany(softwareAG);
        System.out.println("companyId = " + companyId);
    }
}
