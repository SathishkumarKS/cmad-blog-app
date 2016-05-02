package com.cisco.training.cmad.blog.handler;

import com.cisco.training.cmad.blog.exception.BadRequest;
import com.cisco.training.cmad.blog.exception.CompanyAlreadyExists;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.exception.UserAlreadyExists;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by satkuppu on 30/04/16.
 */
public class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static void handleException(AsyncResult<Object> res, RoutingContext routingContext) {
        if(res.cause() instanceof UserAlreadyExists
                || res.cause() instanceof CompanyAlreadyExists
                || res.cause() instanceof BadRequest) {
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                    .end(res.cause().getMessage());
        } else if(res.cause() instanceof DataNotFound) {
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
                    .end(res.cause().getMessage());
        } else {
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                    .end(HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase());
        }

    }

}
