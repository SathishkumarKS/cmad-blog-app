package com.cisco.training.cmad.blog.service.test;

import com.cisco.training.cmad.blog.dao.BlogDAO;
import com.cisco.training.cmad.blog.dto.CommentDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.model.Blog;
import com.cisco.training.cmad.blog.service.BlogService;
import com.cisco.training.cmad.blog.service.BlogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by satkuppu on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddCommentTest {

    @Mock
    BlogDAO blogDAO;

    @InjectMocks
    BlogService blogService = new BlogServiceImpl(blogDAO);

    @Test
    public void whenAddCommentIsSuccessful_ShouldReturnSuccess() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Comment");
        commentDTO.setBlogId("123");

        Blog blogStub = Mockito.mock(Blog.class);
        Mockito.when(blogDAO.getBlog("123")).thenReturn(blogStub);
        Mockito.when(blogStub.addComment("Comment",null,null,null)).thenReturn(blogStub);
        Mockito.when(blogDAO.saveBlog(blogStub)).thenReturn("123");

        Assert.assertEquals(blogService.addComment(commentDTO), "Comment added successfully");
    }

    @Test(expected = DataNotFound.class)
    public void whenBlogIsInvalid_ShouldThrowDataNotFound() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Comment");
        commentDTO.setBlogId("123");

        Mockito.when(blogDAO.getBlog("123")).thenReturn(null);
        Assert.assertEquals(blogService.addComment(commentDTO), "Comment added successfully");
    }

    @Test
    public void whenCommentIsSaved_ShouldStoreAllDataFromDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Comment");
        commentDTO.setBlogId("123");
        commentDTO.setUserFirst("123");
        commentDTO.setUserLast("123");
        commentDTO.setUserId("123");

        Blog blogStub = Mockito.mock(Blog.class);
        Mockito.when(blogDAO.getBlog("123")).thenReturn(blogStub);
        Mockito.when(blogDAO.saveBlog(Mockito.any())).thenReturn("123");

        blogService.addComment(commentDTO);

        ArgumentCaptor<String> comment = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userFirst = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userLast = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userId = ArgumentCaptor.forClass(String.class);
        Mockito.verify(blogStub).addComment(comment.capture(), userFirst.capture(), userLast.capture(), userId.capture());

        Assert.assertEquals(comment.getValue(), "Comment");
        Assert.assertEquals(userFirst.getValue(), "123");
        Assert.assertEquals(userLast.getValue(), "123");
        Assert.assertEquals(userLast.getValue(), "123");
    }
}
