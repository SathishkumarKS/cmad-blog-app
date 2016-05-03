package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyRegistrationStatusDTO;
import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Site;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by satkuppu on 28/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterCompanyTest {
    @Mock
    private CompanyDAO companyDAOStub;

    @InjectMocks
    private CompanyService companyService = new CompanyServiceImpl(companyDAOStub);

    @Test
    public void whenCompanyRegistrationIsSuccessful_ShouldReturnTheIdInResponse() {

        Mockito.when(companyDAOStub.saveCompany(Mockito.any())).thenReturn("123");

        CompanyRegistrationStatusDTO result = companyService.registerCompany("CISCO",".cisco.com","IT");

        assertNotNull(result.getCompanyId());
        assertNotNull(result.getSiteId());
        assertNotNull(result.getDeptId());
        assertNotNull(result.getDeptName());
    }

    @Test
    public void whenCompanyIsRegistered_ShouldCreateCompanySiteAndDeparment() {

        ArgumentCaptor<Company> argument = ArgumentCaptor.forClass(Company.class);
        Mockito.when(companyDAOStub.saveCompany(argument.capture())).thenReturn("123");

        companyService.registerCompany("CISCO",".cisco.com","IT");

        final Company company = argument.getValue();
        assertEquals("CISCO", company.getName());
        final Collection<Site> sites = company.getSites().get().values();
        String siteName = sites.stream().map(site -> {
            return site.getName();
        }).findFirst().get();
        assertEquals("Default Site", siteName);

        String departmentName = sites.stream().flatMap(site -> {
            return site.getDepartments().get().values().stream().map(department -> {
                return department.getName();
            });
        }).findFirst().get();
        assertEquals("IT", departmentName);
    }

    @Test(expected = CompanyAlreadyExists.class)
    public void whenCompanyWithSameNameExists_ShouldThrowCompanyAlreadyExists() {
        Mockito.when(companyDAOStub.saveCompany(Mockito.any())).thenThrow(new CompanyAlreadyExists("Company already exists", null));

        companyService.registerCompany("CISCO",".cisco.com","IT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCompanyNameIsNull_ShouldThrowIlegalArgumentException() {
        companyService.registerCompany(null,".cisco.com","IT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDepartmentNameIsNull_ShouldThrowIlegalArgumentException() {
        companyService.registerCompany("CISCO",".cisco.com",null);
    }
}