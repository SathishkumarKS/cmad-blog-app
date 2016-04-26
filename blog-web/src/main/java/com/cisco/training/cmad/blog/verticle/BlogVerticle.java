package com.cisco.training.cmad.blog.verticle;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.config.MorphiaService;
import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by satkuppu on 4/25/16.
 */
public class BlogVerticle extends AbstractVerticle {
    @Inject
    private CompanyService companyService;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Guice.createInjector(new BlogModule()).injectMembers(this);

        // Create a router object.
        Router router = Router.router(vertx);
        router.route("/assets/*").handler(StaticHandler.create("assets"));
        router.get("/api/companies").handler(this::getAllCompanies);
        router.get("/api/companies/:companyId/sites").handler(this::getSites);
        router.get("/api/companies/:companyId/sites/:siteId/departments").handler(this::getDepartments);

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
}
