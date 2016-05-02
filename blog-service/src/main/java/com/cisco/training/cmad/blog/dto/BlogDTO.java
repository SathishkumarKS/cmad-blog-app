package com.cisco.training.cmad.blog.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
@Data
public class BlogDTO {

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
