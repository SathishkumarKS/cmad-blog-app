package com.cisco.training.cmad.blog.verticle;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import com.cisco.training.cmad.blog.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

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
                .setSessionTimeout(30*60*1000)); //30 minutes

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
        String userId = userService.registerUser(reg);
        System.out.println("userId = " + userId);
        routingContext.response().setStatusCode(201).end();
    }

    private void getAllCompanies(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(companyService.getAllCompanies()));
    }

    private void getSites(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(companyService.getSites(companyId)));

    }

    private void getDepartments(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        String siteId = routingContext.request().getParam("siteId");
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(companyService.getDepartments(companyId, siteId)));

    }

    private void authenticateUser(RoutingContext routingContext) {
        String payload = routingContext.getBodyAsString();
        UserAuthDTO userAuthDTO = Json.decodeValue(payload, UserAuthDTO.class);
        userService.authenticateUser(userAuthDTO);

        if(userService.authenticateUser(userAuthDTO)) {
            routingContext.response().setStatusCode(200).end();
        } else {
            routingContext.response().setStatusCode(401).end();
        }
    }
}
