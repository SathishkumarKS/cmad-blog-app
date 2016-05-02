package com.cisco.training.cmad.blog.model;

import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

/**
 * Created by satkuppu on 4/23/16.
 */
@Entity(value = "blogs", noClassnameStored = true)
@ToString
public class Blog {
    @Id
    private ObjectId id;

    @Getter
    private String title;
    @Getter
    private String content;
    private Set<String> tags;
    @Getter
    private String userFirst;
    @Getter
    private String userLast;
    @Getter
    private String userId;
    @Getter
    private Date createdAt;
    @Embedded
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
        if(this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.addAll(tags);
        return this;
    }

    public Blog addComment(String content, String userFirst, String userLast, String userId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserFirst(userFirst);
        comment.setUserLast(userLast);
        comment.setUserId(userId);
        comment.setCommentedAt(new Date());

        if(this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);

        return this;
    }

    public Optional<List<Comment>> getComments() {
        return Optional.ofNullable(this.comments);
    }

    public Optional<Set<String>> getTags() {
        return Optional.ofNullable(this.tags);
    }

    public String getId() {
        return id != null ? id.toString() : "";
    }
}
