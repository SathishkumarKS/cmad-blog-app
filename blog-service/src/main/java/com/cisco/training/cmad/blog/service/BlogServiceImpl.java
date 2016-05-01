package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.dto.CommentDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.mapper.BlogMapper;
import com.cisco.training.cmad.blog.model.Blog;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

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
    public String addBlog(BlogDTO blog) {
        return blogDAO.save(blogMapper.toBlog(blog)).getId().toString();
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogMapper.toBlogDTOList(blogDAO.createQuery().order("-createdAt").asList());
    }

    @Override
    public List<BlogDTO> getBlogsByTag(String tagName) {
        return blogMapper.toBlogDTOList(blogDAO.getByTag(tagName));
    }

    @Override
    public String addComment(CommentDTO comment) {
        Blog blog = blogDAO.findOne("id", new ObjectId(comment.getBlogId()));
        if(blog != null) {
            blog.addComment(comment.getContent(), comment.getUserFirst(), comment.getUserLast(), comment.getUserId());
            blogDAO.save(blog);
            return "Comment added successfully";
        }
        throw new DataNotFound("Blog not found");
    }
}
