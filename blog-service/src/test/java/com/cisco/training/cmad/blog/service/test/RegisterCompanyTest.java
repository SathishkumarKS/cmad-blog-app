package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyRegistrationStatusDTO;
import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.mapper.CompanyMapper;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Site;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Key;

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
    private CompanyService companyService = new CompanyServiceImpl(companyDAOStub, new CompanyMapper());

    @Test
    public void whenCompanyRegistrationIsSuccessful_ShouldReturnTheIdInResponse() {
        Key<Company> companyKeyStub = Mockito.mock(Key.class);
        BDDMockito.when(companyKeyStub.getId()).thenReturn(new ObjectId());

        Mockito.when(companyDAOStub.save(Mockito.any())).thenReturn(companyKeyStub);

        CompanyRegistrationStatusDTO result = companyService.registerCompany("CISCO",".cisco.com","IT");

        assertNotNull(result.getCompanyId());
        assertNotNull(result.getSiteId());
        assertNotNull(result.getDeptId());
        assertNotNull(result.getDeptName());
    }

    @Test
    public void whenCompanyIsRegistered_ShouldCreateCompanySiteAndDeparment() {
        Key<Company> companyKeyStub = Mockito.mock(Key.class);
        BDDMockito.when(companyKeyStub.getId()).thenReturn(new ObjectId());

        ArgumentCaptor<Company> argument = ArgumentCaptor.forClass(Company.class);
        Mockito.when(companyDAOStub.save(argument.capture())).thenReturn(companyKeyStub);

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
        Company companyStub = Mockito.mock(Company.class);
        Mockito.when(companyDAOStub.save(Mockito.any())).thenThrow(Mockito.mock(DuplicateKeyException.class));

        companyService.registerCompany("CISCO",".cisco.com","IT");

    }
}