package com.cisco.training.cmad.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
@Data
public class BlogDTO {
    // {"content":"Java 8 is awesome...","title":"Java 8 is awesome","tags":"Java"}

    private String id;
    private String content;
    private String tags;
    private String title;
    private String userFirst;
    private String userLast;
    private String userId;
    private Date date;
    private List<CommentDTO> comments;
}
