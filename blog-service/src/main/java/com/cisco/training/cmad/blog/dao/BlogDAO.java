package com.cisco.training.cmad.blog.dao;

import com.cisco.training.cmad.blog.model.Blog;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public interface BlogDAO extends DAO<Blog, ObjectId> {
    public List<Blog> getByTag(String tagName);
}
