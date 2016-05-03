package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.model.Company;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import java.util.List;

/**
 * Created by satkuppu on 4/23/16.
 */
public interface CompanyDAO extends DAO<Company, ObjectId> {

    public String saveCompany(Company company) throws CompanyAlreadyExists;
    public Company getCompany(String companyId);
    public List<Company> getAllCompanies();
    }
