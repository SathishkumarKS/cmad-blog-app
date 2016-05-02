package com.cisco.training.cmad.blog.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by satkuppu on 30/04/16.
 */
@Data @ToString
public class CommentDTO {
    private String blogId;
    private String content;
    private String userFirst;
    private String userLast;
    private String userId;
    private Date date;
}
