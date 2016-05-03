package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.service.BlogService;
import com.cisco.training.cmad.blog.service.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by satkuppu on 02/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAllBlogsTest {
    @Mock
    BlogDAO blogDAOStub;

    @InjectMocks
    private BlogService blogService = new BlogServiceImpl(blogDAOStub);

    @Test
    public void whenBlogsExist_ShouldReturnListOfBlogs() {
        List<Blog> blogs = Arrays.asList(new Blog()
                .withTags(Arrays.asList("Java","Vertx"))
                .addComment("123","123","!23","!23"));

        Mockito.when(blogDAOStub.getAllBlogs()).thenReturn(blogs);

        List<BlogDTO> blogDTOList = blogService.getAllBlogs();

        Assert.assertEquals(blogs.size(), blogDTOList.size());
    }


}
