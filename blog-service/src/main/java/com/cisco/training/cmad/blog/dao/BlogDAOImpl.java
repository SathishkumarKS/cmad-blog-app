package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogDAOImpl extends BasicDAO<Blog, ObjectId> implements BlogDAO  {

    @Inject
    protected BlogDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public List<Blog> getByTag(String tagName) {
        return createQuery().order("-createdAt").field("tags").contains(tagName).asList();
    }

}
