package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

/**
 * Created by satkuppu on 27/04/16.
 */
public class UserDAOImpl extends BasicDAO<User, ObjectId> implements UserDAO {

    @Inject
    public UserDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public User getByUserName(String userName) {
        Query<User> query = createQuery().
                field("userName").equal(userName);
        return query.get();
    }
}
