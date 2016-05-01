package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.dto.CommentDTO;

import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public interface BlogService {

    public String addBlog(BlogDTO blog);
    public List<BlogDTO> getAllBlogs();
    public List<BlogDTO> getBlogsByTag(String tagName);
    public String addComment(CommentDTO comment);

}
