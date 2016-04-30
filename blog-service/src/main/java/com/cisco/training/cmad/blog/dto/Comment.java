package com.cisco.training.cmad.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by satkuppu on 30/04/16.
 */
@Data
public class Comment {
    private String content;
    private String userFirst;
    private String userLast;
    private String userId;
    private Date date;
}
