package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

/**
 * Created by satkuppu on 25/04/16.
 */
public interface UserDAO extends DAO<User, ObjectId> {
    public User getByUserName(String userName);
}
