package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.model.Company;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.List;

/**
 * Created by satkuppu on 27/04/16.
 */
@Singleton
public class CompanyDAOImpl extends BasicDAO<Company, ObjectId> implements CompanyDAO {

    @Inject
    public CompanyDAOImpl(Datastore ds) {
        super(ds);
    }

    public String saveCompany(Company company) throws CompanyAlreadyExists{
        try {
            return save(company).getId().toString();
        } catch (DuplicateKeyException dke) {
            throw new CompanyAlreadyExists("Company already exists with same name", dke);
        }
    }

    @Override
    public Company getCompany(String companyId) {
        return findOne("id", new ObjectId(companyId));
    }

    public List<Company> getAllCompanies() {
        return find().asList();
    }
}
