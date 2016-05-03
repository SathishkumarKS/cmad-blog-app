package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.Optional;

/**
 * Created by satkuppu on 27/04/16.
 */
@Singleton
public class UserDAOImpl extends BasicDAO<User, ObjectId> implements UserDAO {

    @Inject
    public UserDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Optional<User> getByUserName(String userName) {
        Query<User> query = createQuery().
                field("userName").equal(userName);
        return Optional.ofNullable(query.get());
    }
}
