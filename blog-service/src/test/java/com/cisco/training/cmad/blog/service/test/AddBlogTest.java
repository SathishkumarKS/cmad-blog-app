package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.service.BlogService;
import com.cisco.training.cmad.blog.service.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by satkuppu on 02/05/16.
 */
@RunWith(MockitoJUnitRunner.class)

public class AddBlogTest {
    @Mock
    BlogDAO blogDAOStub;

    @InjectMocks
    private BlogService blogService = new BlogServiceImpl(blogDAOStub);

    @Test
    public void whenAddBlogIsSuccessful_ShouldReturnNonNullBlogId() {
        BDDMockito.when(blogDAOStub.saveBlog(Mockito.any())).thenReturn("123");

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setContent("Test blog");

        String blogId = blogService.addBlog(blogDTO);

        Assert.assertNotNull(blogId);
    }

    @Test
    public void whenSavingBlog_BlogPropertiesShouldBeCopiedProperly() {

        ArgumentCaptor<Blog> argument = ArgumentCaptor.forClass(Blog.class);
        Mockito.when(blogDAOStub.saveBlog(argument.capture())).thenReturn("123");

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setContent("Test blog");
        blogDTO.setUserFirst("Sathishkumar K");

        blogService.addBlog(blogDTO);

        Assert.assertEquals(argument.getValue().getContent(), blogDTO.getContent());
        Assert.assertEquals(argument.getValue().getUserFirst(), blogDTO.getUserFirst());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingNullBlog_ShouldThrowIllegalArgumentException() {
        blogService.addBlog(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBlogContentIsMissing_ShouldThrowIllegalArgumentException() {
        blogService.addBlog(new BlogDTO());
    }

}
