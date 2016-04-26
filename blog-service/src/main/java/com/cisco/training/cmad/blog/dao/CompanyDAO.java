package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Company;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by satkuppu on 4/23/16.
 */
public class CompanyDAO extends BasicDAO<Company, ObjectId> {

    @Inject
    public CompanyDAO(Datastore ds) {
        super(ds);
    }
}
