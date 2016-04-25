package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import org.mongodb.morphia.Datastore;

/**
 * Created by satkuppu on 4/23/16.
 */
public class CompanyClient {

    public static void main(String[] args) {
        Datastore datastore = new MorphiaService().getDatastore();

/*
        Department dept = new Department("IT");
        Site acmeSite = new Site("Site1").addDepartment(dept);
        Company acme = new Company("Acme Inc");
        acme.setSubDomain("acme");
        acme.addSite(acmeSite);
        Key<Company> companyId = dao.save(acme);
        System.out.println("companyId = " + companyId);
*/
        CompanyService companyService = new CompanyServiceImpl(new CompanyDAO(Company.class, datastore));
//        String acmeId = companyService.registerCompany("Acme Inc", "Site1", "acme", "IT");
//        String ciscoId = companyService.registerCompany("CISCO", "Blogger", "blogs", "IT");
        System.out.println("companyService.getSites(ciscoId) = " + companyService.getSites("571d8f20d4a3fd35a649c2ec"));

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
}
