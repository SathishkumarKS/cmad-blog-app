package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Company;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.dao.DAO;

/**
 * Created by satkuppu on 4/23/16.
 */
public interface CompanyDAO extends DAO<Company, ObjectId> {
}
