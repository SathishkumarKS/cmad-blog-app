package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import java.util.Optional;

/**
 * Created by satkuppu on 25/04/16.
 */
public interface UserDAO extends DAO<User, ObjectId> {
    public Optional<User> getByUserName(String userName);
}
