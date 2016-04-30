package com.cisco.training.cmad.blog.verticle;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.dto.*;
import com.cisco.training.cmad.blog.exception.BadRequest;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.handler.BlogRequestHandler;
import com.cisco.training.cmad.blog.handler.CompanyRequestHandler;
import com.cisco.training.cmad.blog.handler.ExceptionHandler;
import com.cisco.training.cmad.blog.handler.UserRequestHandler;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.UserService;
import com.cisco.training.cmad.blog.web.module.BlogWebModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by satkuppu on 4/25/16.
 */
public class BlogVerticle extends AbstractVerticle {
    @Inject
    private CompanyRequestHandler companyRequestHandler;
    @Inject
    private UserRequestHandler userRequestHandler;
    @Inject
    private BlogRequestHandler blogRequestHandler;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Injector injector = Guice.createInjector(new BlogWebModule());
        injector.injectMembers(this);

        // Create a router object.
        Router router = Router.router(vertx);
        /* Session Handler */
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler
                .create(LocalSessionStore.create(vertx))
                .setCookieHttpOnlyFlag(true)
                .setCookieSecureFlag(false)
                .setSessionTimeout(1800000L)); //30 minutes

        router.get("/Services/rest/company").handler(companyRequestHandler::getAllCompanies);
        router.get("/Services/rest/company/:companyId/sites").handler(companyRequestHandler::getSites);
        router.get("/Services/rest/company/:companyId/sites/:siteId/departments").handler(companyRequestHandler::getDepartments);
        router.get("/Services/rest/blogs").handler(blogRequestHandler::getBlogs);
        router.get("/Services/rest/user").handler(userRequestHandler::getSignedInUser);

        router.route().handler(BodyHandler.create());
        router.post("/Services/rest/user/register").handler(userRequestHandler::registerUser);
        router.post("/Services/rest/user/auth").handler(userRequestHandler::authenticateUser);
        router.post("/Services/rest/blogs").handler(blogRequestHandler::addBlog);

        router.route().handler(StaticHandler.create().setCachingEnabled(true)::handle);

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
}
