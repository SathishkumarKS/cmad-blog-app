package com.cisco.training.cmad.blog.handler;

import com.cisco.training.cmad.blog.dto.BlogDTO;
import com.cisco.training.cmad.blog.dto.CommentDTO;
import com.cisco.training.cmad.blog.dto.UserDTO;
import com.cisco.training.cmad.blog.service.BlogService;
import com.cisco.training.cmad.blog.service.UserService;
import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogRequestHandler {

    @Inject
    BlogService blogService;
    @Inject
    UserService userService;

    public void addBlog(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        BlogDTO reg = Json.decodeValue(payload, BlogDTO.class);
        routingContext.vertx().executeBlocking(future -> {

            UserDTO userDTO = userService.getUser(routingContext.session().get("id"));
            reg.setUserFirst(userDTO.getFirst());
            reg.setUserLast(userDTO.getLast());
            reg.setUserId(userDTO.getId());

            String blogId = blogService.addBlog(reg);
            future.complete(blogId);
        }, res -> {
            if(res.succeeded()) {
                routingContext.response().setStatusCode(HttpResponseStatus.CREATED.code()).end(res.result().toString());
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }
        });
    }

    public void getBlogs(RoutingContext routingContext) {
        String tagName = routingContext.request().getParam("tag");
        List<BlogDTO> blogList;
        if(tagName != null && !tagName.trim().isEmpty()) {
            blogList = blogService.getBlogsByTag(tagName);
        } else {
            blogList = blogService.getAllBlogs();
        }
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setStatusCode(HttpResponseStatus.OK.code())
                .end(Json.encodePrettily(blogList));
    }

    public void addComment(RoutingContext routingContext) {
        String blogId = routingContext.request().getParam("blogId");
        CommentDTO comment = Json.decodeValue(routingContext.getBodyAsString(), CommentDTO.class);
        comment.setBlogId(blogId);
        routingContext.vertx().executeBlocking(future -> {

            UserDTO userDTO = userService.getUser(routingContext.session().get("id"));
            comment.setUserFirst(userDTO.getFirst());
            comment.setUserLast(userDTO.getLast());
            comment.setUserId(userDTO.getId());

            future.complete(blogService.addComment(comment));
        }, res -> {
            if(res.succeeded()) {
                routingContext.response().setStatusCode(HttpResponseStatus.CREATED.code()).end(res.result().toString());
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }
        });
    }
}
