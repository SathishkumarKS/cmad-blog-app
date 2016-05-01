package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.CompanyDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.mapper.CompanyMapper;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satkuppu on 01/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAllCompaniesTest {
    @Mock
    private CompanyDAO companyDAOStub;

    @Mock
    QueryResults<Company> companiesResultStub;

    @InjectMocks
    private CompanyService companyService = new CompanyServiceImpl(companyDAOStub, new CompanyMapper());

    @Test
    public void testGetAllCompanies() {
        List<Company> companies = new ArrayList<>();
        Company cisco = Mockito.mock(Company.class);
        Mockito.when(cisco.getId()).thenReturn(ObjectId.get());
        companies.add(cisco);
        companies.add(cisco);

        BDDMockito.given(companyDAOStub.find()).willReturn(companiesResultStub);
        BDDMockito.given(companiesResultStub.asList()).willReturn(companies);

        List<CompanyDTO> companyList = companyService.getAllCompanies();

        Assert.assertEquals("Company size should match", 2, companyList.size());
        Assert.assertEquals("Company names should match", companies.get(0).getName(), companyList.get(0).getCompanyName());
    }

    @Test(expected = DataNotFound.class)
    public void whenNoCompaniesExist_ShouldThrowDataNotFound() {
        BDDMockito.given(companyDAOStub.find()).willReturn(null);

        List<CompanyDTO> companyList = companyService.getAllCompanies();
    }
}
