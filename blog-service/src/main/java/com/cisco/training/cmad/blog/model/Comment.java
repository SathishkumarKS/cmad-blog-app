package com.cisco.training.cmad.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created by satkuppu on 01/05/16.
 */
@Data
public class Comment implements Comparable {
    private String content;
    private String userFirst;
    private String userLast;
    private String userId;
    private Date commentedAt;

    public Comment() {

    }

    public Comment(String content, String userFirst, String userLast, String userId) {
        this.content = content;
        this.userFirst = userFirst;
        this.userLast = userLast;
        this.userId = userId;
        this.commentedAt = new Date();
    }

    @Override
    public int compareTo(Object o) {
        return this.commentedAt.compareTo(((Comment) o).commentedAt);
    }
}