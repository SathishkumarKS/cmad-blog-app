package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.service.BlogService;
import com.cisco.training.cmad.blog.service.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by satkuppu on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetBlogsByTagTest {
    @Mock
    BlogDAO blogDAOStub;

    @InjectMocks
    private BlogService blogService = new BlogServiceImpl(blogDAOStub);

    @Test
    public void whenTagListisEmpty_ShouldReturnEmptyString() {
        BDDMockito.when(blogDAOStub.getByTag(Mockito.any())).thenReturn(Arrays.asList(new Blog()));

        List<BlogDTO> blogs = blogService.getBlogsByTag("Java");

        Assert.assertEquals(blogs.get(0).getTags(), "");
    }

    @Test
    public void whenTagListisNonEmpty_ShouldReturnCommaSeparatedTags() {
        Blog blog = new Blog();
        blog.withTags(Arrays.asList("Java", "Vertx"));
        BDDMockito.when(blogDAOStub.getByTag(Mockito.any())).thenReturn(Arrays.asList(blog));

        List<BlogDTO> blogs = blogService.getBlogsByTag("Java");

        Assert.assertEquals(blogs.get(0).getTags(), "Java, Vertx");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTagNameIsEmpty_ShouldThrowIllegalArgumentException() {
        blogService.getBlogsByTag(null);
    }

}

