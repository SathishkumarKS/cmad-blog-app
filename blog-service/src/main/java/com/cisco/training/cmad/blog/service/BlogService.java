package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.Blog;

import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public interface BlogService {

    public String addBlog(Blog blog);
    public List<Blog> getAllBlogs();
    public List<Blog> getBlogsByTag(String tagName);

}
