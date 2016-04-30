package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.Blog;
import com.cisco.training.cmad.blog.mapper.BlogMapper;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogServiceImpl implements BlogService {

    private BlogDAO blogDAO;
    private BlogMapper blogMapper;

    @Inject
    public BlogServiceImpl(BlogDAO blogDAO, BlogMapper blogMapper) {
        this.blogDAO = blogDAO;
        this.blogMapper = blogMapper;
    }

    @Override
    public String addBlog(Blog blog) {
        return blogDAO.save(blogMapper.toBlog(blog)).getId().toString();
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogMapper.toBlogDTOList(blogDAO.find().asList());
    }
}
