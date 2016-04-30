package com.cisco.training.cmad.blog.mapper;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.model.Site;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogMapper {

    public Blog toBlog(com.cisco.training.cmad.blog.dto.Blog blogDTO) {
        return new com.cisco.training.cmad.blog.model.Blog(blogDTO.getTitle(), blogDTO.getContent(),
                        blogDTO.getUserFirst(), blogDTO.getUserLast(), blogDTO.getUserId())
                        .withTags(Arrays.asList(blogDTO.getTags().split("\\s*,\\s*")));
    }

    public com.cisco.training.cmad.blog.dto.Blog toBlogDTO(Blog blog) {
        com.cisco.training.cmad.blog.dto.Blog blogDTO = new com.cisco.training.cmad.blog.dto.Blog();
        blogDTO.setId(blog.getId().toString());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setContent(blog.getContent());
        blogDTO.setUserFirst(blog.getUserFirst());
        blogDTO.setUserLast(blog.getUserLast());
        blogDTO.setUserId(blog.getUserId());
        blogDTO.setDate(blog.getCreatedAt());
        blogDTO.setComments(blog.getComments());
        blogDTO.setTags(blog.getTags()
                        .stream().collect(Collectors.joining(", ")));

        return blogDTO;
    }

    public List<com.cisco.training.cmad.blog.dto.Blog> toBlogDTOList(List<Blog> blogList) {
        return blogList.stream().map(blog -> {
            return toBlogDTO(blog);
        }).collect(Collectors.toCollection(ArrayList<com.cisco.training.cmad.blog.dto.Blog>::new));
    }

}
