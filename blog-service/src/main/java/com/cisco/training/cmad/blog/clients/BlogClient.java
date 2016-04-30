package com.cisco.training.cmad.blog.clients;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.Blog;
import com.cisco.training.cmad.blog.service.BlogService;
import com.google.inject.Guice;
import com.google.inject.Inject;

import java.util.Date;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogClient {

    @Inject
    BlogService blogService;
    @Inject
    BlogDAO blogDAO;

    public static void main(String[] args) {
        new BlogClient().searchByTag();
    }

    public BlogClient() {
        Guice.createInjector(new BlogModule()).injectMembers(this);
    }

    public void addBlog() {
        Blog blog = new Blog();
        blog.setContent("Java 8 streams are awesome!!!");
        blog.setTitle("Java 8 Streams");
        blog.setUserId("5723b28803989dd62c3a5601");
        blog.setUserFirst("Sathishkumar");
        blog.setUserLast("K");
        blog.setTags("Java, Functional Programming");

        blogService.addBlog(blog);
    }

    public void searchByTag() {
        System.out.println(blogService.getBlogsByTag("Java"));
    }
}
