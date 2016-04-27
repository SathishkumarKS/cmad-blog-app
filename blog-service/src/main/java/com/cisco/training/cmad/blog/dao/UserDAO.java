package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.dao.DAO;

/**
 * Created by satkuppu on 25/04/16.
 */
public interface UserDAO extends DAO<User, ObjectId> {
    public User getByUserName(String userName);
}
