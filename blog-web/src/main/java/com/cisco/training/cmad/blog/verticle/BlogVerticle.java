package com.cisco.training.cmad.blog.verticle;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.DataNotFoundException;
import com.cisco.training.cmad.blog.exception.UserAlreadyExistsException;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by satkuppu on 4/25/16.
 */
public class BlogVerticle extends AbstractVerticle {
    @Inject
    private CompanyService companyService;
    @Inject
    private UserService userService;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Guice.createInjector(new BlogModule()).injectMembers(this);

        // Create a router object.
        Router router = Router.router(vertx);
        router.get("/Services/rest/company").handler(this::getAllCompanies);
        router.get("/Services/rest/company/:companyId/sites").handler(this::getSites);
        router.get("/Services/rest/company/:companyId/sites/:siteId/departments").handler(this::getDepartments);

        router.route().handler(BodyHandler.create());
        router.post("/Services/rest/user/register").handler(this::registerUser);
        router.post("/Services/rest/user/auth").handler(this::authenticateUser);

        router.route().handler(StaticHandler.create().setCachingEnabled(true)::handle);

        /* Session Handler */
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler
                .create(LocalSessionStore.create(vertx))
                .setCookieHttpOnlyFlag(true)
                .setCookieSecureFlag(true)
                .setSessionTimeout(1800000L)); //30 minutes

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration, default to 8080.
                        config().getInteger("http.port", 8080),
                        result -> {
                            if (result.succeeded()) {
                                startFuture.complete();
                            } else {
                                startFuture.fail(result.cause());
                            }
                        }
                );
    }

    private void registerUser(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        UserRegistrationDTO reg = Json.decodeValue(payload, UserRegistrationDTO.class);
        vertx.executeBlocking(future -> {
            try {
                String userId = userService.registerUser(reg);
                future.complete(userId);
            } catch(Throwable e) {
                future.fail(e);
            }
        }, res -> {
            if(res.succeeded()) {
                routingContext.response().setStatusCode(HttpResponseStatus.CREATED.code()).end(res.result().toString());
            } else {
                if(res.cause() instanceof UserAlreadyExistsException) {
                    routingContext.response()
                            .setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
                            .end(res.cause().getMessage());
                } else {
                    routingContext.response()
                            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                            .end(HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase());
                }
            }
        });
    }

    private void getAllCompanies(RoutingContext routingContext) {
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .end(Json.encodePrettily(companyService.getAllCompanies()));
    }

    private void getSites(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        vertx.executeBlocking(future -> {
            try {
                List<SiteDTO> sites = companyService.getSites(companyId);
                future.complete(sites);
            } catch(DataNotFoundException e) {
                future.fail(e);
            }
        }, res -> {
            if(res.succeeded()) {
                routingContext.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .end(Json.encodePrettily(res.result()));
            } else {
                if(res.cause() instanceof DataNotFoundException) {
                    routingContext.response()
                            .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
                            .end(res.cause().getMessage());
                } else {
                    routingContext.response()
                            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                            .end(HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase());
                }
            }
        });
    }

    private void getDepartments(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        String siteId = routingContext.request().getParam("siteId");
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .end(Json.encodePrettily(companyService.getDepartments(companyId, siteId)));

    }

    private void authenticateUser(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        UserAuthDTO userAuthDTO = Json.decodeValue(payload, UserAuthDTO.class);
        userService.authenticateUser(userAuthDTO);

        if(userService.authenticateUser(userAuthDTO)) {
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
