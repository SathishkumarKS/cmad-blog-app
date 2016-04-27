package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Company;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by satkuppu on 27/04/16.
 */
public class CompanyDAOImpl extends BasicDAO<Company, ObjectId> implements CompanyDAO {

    @Inject
    public CompanyDAOImpl(Datastore ds) {
        super(ds);
    }

}
