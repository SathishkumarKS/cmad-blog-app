package com.cisco.training.cmad.blog.mapper;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.dto.CommentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.model.Comment;
import com.cisco.training.cmad.blog.model.Site;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogMapper {

    public Blog toBlog(BlogDTO blogDTO) {
        return new Blog(blogDTO.getTitle(), blogDTO.getContent(),
                blogDTO.getUserFirst(), blogDTO.getUserLast(), blogDTO.getUserId())
                .withTags(Arrays.asList(blogDTO.getTags().split("\\s*,\\s*")));
    }

    public BlogDTO toBlogDTO(Blog blog) {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blog.getId().toString());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setContent(blog.getContent());
        blogDTO.setUserFirst(blog.getUserFirst());
        blogDTO.setUserLast(blog.getUserLast());
        blogDTO.setUserId(blog.getUserId());
        blogDTO.setDate(blog.getCreatedAt());
        blogDTO.setComments(toCommentDTOList(blog, blog.getComments().orElseGet(ArrayList::new)));
        blogDTO.setTags(blog.getTags()
                .stream().collect(Collectors.joining(", ")));

        return blogDTO;
    }

    public List<BlogDTO> toBlogDTOList(List<Blog> blogList) {
        return blogList.stream().map(blog -> {
            return toBlogDTO(blog);
        }).collect(Collectors.toCollection(ArrayList<BlogDTO>::new));
    }

    public CommentDTO toCommentDTO(Blog blog, Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBlogId(blog.getId().toString());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserId(comment.getUserId());
        commentDTO.setUserFirst(comment.getUserFirst());
        commentDTO.setUserLast(comment.getUserLast());
        commentDTO.setDate(comment.getCommentedAt());

        return commentDTO;
    }

    public List<CommentDTO> toCommentDTOList(Blog blog, List<Comment> comments) {
        return comments.stream().map(comment -> {
            return toCommentDTO(blog, comment);
        }).collect(Collectors.toList());
    }
}
