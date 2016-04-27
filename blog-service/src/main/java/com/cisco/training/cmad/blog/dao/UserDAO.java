package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserDAO extends BasicDAO<User, ObjectId> {
    @Inject
    public UserDAO(Datastore ds) {
        super(ds);
    }
}
