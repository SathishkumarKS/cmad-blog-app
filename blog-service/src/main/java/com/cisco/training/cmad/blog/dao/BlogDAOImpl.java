package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Blog;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

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
        return createQuery().order("-createdAt").field("tags").containsIgnoreCase(tagName).asList();
    }

    @Override
    public List<Blog> getAllBlogs() {
        return createQuery().order("-createdAt").asList();
    }

    @Override
    public String saveBlog(Blog blog) {
        return save(blog).getId().toString();
    }

    @Override
    public Blog getBlog(String blogId) {
        return findOne("id", new ObjectId(blogId));
    }
}
