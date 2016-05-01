package com.cisco.training.cmad.blog.handler;

import com.cisco.training.cmad.blog.dto.*;
import com.cisco.training.cmad.blog.exception.BadRequest;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.UserService;
import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.core.MediaType;

/**
 * Created by satkuppu on 30/04/16.
 */
public class UserRequestHandler {

    @Inject
    UserService userService;

    @Inject
    CompanyService companyService;

    public void getSignedInUser(RoutingContext routingContext) {
        String signedIn = routingContext.request().getParam("signedIn");

        routingContext.vertx().executeBlocking(future -> {
            if(signedIn != null && "TRUE".equalsIgnoreCase(signedIn.trim())) {
                String userName = routingContext.session().get("id");
                if(userName != null) {
                    UserDTO user = userService.getUser(userName);
                    future.complete(user);
                } else {
                    throw new BadRequest("Please log in");
                }
            } else {
                throw new BadRequest("Please log in");
            }
        }, res -> {
            if(res.succeeded()) {
                if(res.result() instanceof UserDTO) {
                    routingContext.response()
                            .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(Json.encodePrettily(res.result()));
                }
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }

        });
    }

    public void registerUser(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        UserRegistrationDTO reg = Json.decodeValue(payload, UserRegistrationDTO.class);
        routingContext.vertx().executeBlocking(future -> {
            if(reg.getIsCompany()) {
                CompanyRegistrationStatusDTO companyRegistrationStatusDTO =
                        companyService.registerCompany(reg.getCompanyName(), reg.getSubdomain(), reg.getDeptName());
                reg.setCompanyId(companyRegistrationStatusDTO.getCompanyId());
                reg.setSiteId(companyRegistrationStatusDTO.getSiteId());
                reg.setDeptId(companyRegistrationStatusDTO.getDeptId());
            }
            String userId = userService.registerUser(reg);
            future.complete(userId);
        }, res -> {
            if(res.succeeded()) {
                routingContext.response().setStatusCode(HttpResponseStatus.CREATED.code()).end(res.result().toString());
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }
        });
    }

    public void authenticateUser(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        UserAuthDTO userAuthDTO = Json.decodeValue(payload, UserAuthDTO.class);
        userService.authenticateUser(userAuthDTO);

        if(userService.authenticateUser(userAuthDTO)) {
            routingContext.session().put("id", userAuthDTO.getUserName());
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .end();
        } else {
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                    .end(HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
        }
    }

}
