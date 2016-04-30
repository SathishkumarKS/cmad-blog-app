package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Blog;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogDAOImpl extends BasicDAO<Blog, ObjectId> implements BlogDAO  {

    @Inject
    protected BlogDAOImpl(Datastore ds) {
        super(ds);
    }
}
