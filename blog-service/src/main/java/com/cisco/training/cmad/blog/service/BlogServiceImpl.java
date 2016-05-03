package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.dto.CommentDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.model.Comment;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 30/04/16.
 */
@Singleton
public class BlogServiceImpl implements BlogService {

    private BlogDAO blogDAO;

    @Inject
    public BlogServiceImpl(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }

    @Override
    public String addBlog(BlogDTO blog) {
        Preconditions.checkArgument(blog != null, "Blog can't be null");
        Preconditions.checkArgument(blog.getContent() != null, "Blog content can't be null");
        return blogDAO.saveBlog(BlogMapper.toBlog(blog));
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        return BlogMapper.toBlogDTOList(blogDAO.getAllBlogs());
    }

    @Override
    public List<BlogDTO> getBlogsByTag(String tagName) {
        Preconditions.checkArgument(tagName != null, "Tag name can't be empty");
        return BlogMapper.toBlogDTOList(blogDAO.getByTag(tagName));
    }

    @Override
    public String addComment(CommentDTO comment) {
        Blog blog = blogDAO.getBlog(comment.getBlogId());
        if(blog != null) {
            blog.addComment(comment.getContent(), comment.getUserFirst(), comment.getUserLast(), comment.getUserId());
            blogDAO.saveBlog(blog);
            return "Comment added successfully";
        }
        throw new DataNotFound("Blog not found");
    }

    private static class BlogMapper {
        public static Blog toBlog(BlogDTO blogDTO) {
            return new Blog(blogDTO.getTitle(), blogDTO.getContent(),
                    blogDTO.getUserFirst(), blogDTO.getUserLast(), blogDTO.getUserId())
                    .withTags(blogDTO.getTags() != null ?
                            Arrays.asList(blogDTO.getTags().split("\\s*,\\s*")) : Collections.emptyList());
        }

        public static BlogDTO toBlogDTO(Blog blog) {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(blog.getId().toString());
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setContent(blog.getContent());
            blogDTO.setUserFirst(blog.getUserFirst());
            blogDTO.setUserLast(blog.getUserLast());
            blogDTO.setUserId(blog.getUserId());
            blogDTO.setDate(blog.getCreatedAt());
            blogDTO.setComments(toCommentDTOList(blog, blog.getComments().orElseGet(ArrayList::new)));
            blogDTO.setTags(blog.getTags().map(tags -> {
                return tags.stream().collect(Collectors.joining(", "));
            }).orElse(""));

            return blogDTO;
        }

        public static List<BlogDTO> toBlogDTOList(List<Blog> blogList) {
            return blogList.stream().map(blog -> {
                return toBlogDTO(blog);
            }).collect(Collectors.toCollection(ArrayList<BlogDTO>::new));
        }

        public static CommentDTO toCommentDTO(Blog blog, Comment comment) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setBlogId(blog.getId().toString());
            commentDTO.setContent(comment.getContent());
            commentDTO.setUserId(comment.getUserId());
            commentDTO.setUserFirst(comment.getUserFirst());
            commentDTO.setUserLast(comment.getUserLast());
            commentDTO.setDate(comment.getCommentedAt());

            return commentDTO;
        }

        public static List<CommentDTO> toCommentDTOList(Blog blog, List<Comment> comments) {
            return comments.stream().map(comment -> {
                return toCommentDTO(blog, comment);
            }).collect(Collectors.toList());
        }
    }
}

