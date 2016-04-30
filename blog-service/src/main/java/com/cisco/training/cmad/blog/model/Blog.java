package com.cisco.training.cmad.blog.model;

import com.cisco.training.cmad.blog.dto.Comment;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

/**
 * Created by satkuppu on 4/23/16.
 */
@Entity(value = "blogs", noClassnameStored = true)
public class Blog {
    @Id @Getter
    private ObjectId id;

    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private Set<String> tags;
    @Getter
    private String userFirst;
    @Getter
    private String userLast;
    @Getter
    private String userId;
    @Getter
    private Date createdAt;
    @Embedded @Getter
    private List<Comment> comments;

    public Blog() {
    }

    public Blog(String title, String content, String userFirst, String userLast, String userId) {
        this.title = title;
        this.content = content;
        this.userFirst = userFirst;
        this.userLast = userLast;
        this.userId = userId;
        this.createdAt = new Date();

        this.tags = new HashSet<>();
        this.comments = new ArrayList<>();
    }

    public Blog withTags(List<String> tags) {
        this.tags.addAll(tags);
        return this;
    }

    public Blog addComment() {
        return this;
    }
}
